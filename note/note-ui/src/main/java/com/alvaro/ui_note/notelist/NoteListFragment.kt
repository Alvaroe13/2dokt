package com.alvaro.ui_note.notelist

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.core.domain.UIComponent
import com.alvaro.ui_note.R
import com.alvaro.ui_note.databinding.FragmentNoteListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_note_list), NoteListAdapter.ClickHandler {

    companion object {
        private const val TAG = "NoteListFragment"
        const val NOTE_ID_KEY = "noteId"
    }

    private lateinit var binding: FragmentNoteListBinding
    private val viewModel: NoteListViewModel by viewModels()

    private lateinit var noteAdapter: NoteListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNoteListBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        fabClicked()
        subscribeObservers()
        attachDeleteNoteCallback()
    }

    override fun onResume() {
        super.onResume()
        if (!viewModel.didScreenRotated){
            forceRefresh()
        }
    }

    private fun forceRefresh(){
        viewModel.triggerEvent(NoteListEvents.GetNotes)
    }

    private fun subscribeObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.response.collect { value: UIComponent ->
                    when (value) {
                        is UIComponent.Dialog -> {
                            showDialog(value)
                        }
                        is UIComponent.Toast -> {
                            showToast(value)
                        }
                        else -> {}
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                println("${TAG} triggered ${state}")
                noteAdapter.differAsync.submitList(state.noteList)
            }
        }

    }

    private fun fabClicked() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_NoteListFragment_to_noteDetailFragment)
        }
    }

    private fun initRecyclerView() {
        noteAdapter = NoteListAdapter(this)
        binding.recycler.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showToast(uiComponent: UIComponent.Toast){
        Toast.makeText(requireContext(), uiComponent.message, Toast.LENGTH_LONG).show()
    }

    private fun showDialog(uiComponent: UIComponent.Dialog){
        val builder = AlertDialog.Builder(this.context)
        builder.setTitle(uiComponent.title)
        builder.setMessage(uiComponent.message)
        builder.show()
    }


    private fun attachDeleteNoteCallback() {

       val itemHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = noteAdapter.differAsync.currentList[position]
                viewModel.triggerEvent(NoteListEvents.DeleteNote(note))
            }
        }

        val swipeToDelete = ItemTouchHelper(itemHelper as ItemTouchHelper.SimpleCallback)
        swipeToDelete.attachToRecyclerView(binding.recycler)
    }

    override fun itemClick(noteId: String) {
        val bundle = bundleOf( NOTE_ID_KEY to noteId)
        findNavController().navigate(R.id.action_NoteListFragment_to_noteDetailFragment, bundle)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.didScreenRotated = true
    }

    override fun onPause() {
        super.onPause()
        viewModel.didScreenRotated = false
    }

}



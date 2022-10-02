package com.alvaro.ui_note.notelist

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
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
import com.alvaro.ui_note.notelist.viewstate.DeletionState
import com.alvaro.ui_note.notelist.viewstate.NoteListState
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
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNoteListBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        initViews()
        subscribeObservers()
        createItemTouchHelper()
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun renderState(state: NoteListState){
        noteAdapter.differAsync.submitList(state.noteList)
        when(state.deletionState){
            is DeletionState.Idle -> {
                attachDeleteNoteCallback()
            }
            is DeletionState.OnDeletion ->{
                removeDeleteNoteCallback()
            }
        }
    }

    private fun initViews(){
        noteAdapter = NoteListAdapter(this)
        binding.apply {
            recycler.apply {
                adapter = noteAdapter
                layoutManager = LinearLayoutManager(activity)
            }
            fab.setOnClickListener {
                findNavController().navigate(R.id.action_NoteListFragment_to_noteDetailFragment)
            }
            undoCta.setOnClickListener {
                viewModel.triggerEvent(NoteListEvents.UndoDeletion)
                confirmUndoGroup.isGone = true
                fab.isVisible = true
            }

            confirmCta.setOnClickListener {
                val note = viewModel.noteForDeletion ?: throw NullPointerException("Note for deletion is null and it cannot be")
                viewModel.triggerEvent(NoteListEvents.ConfirmDeletion(note))
                confirmUndoGroup.isGone = true
                fab.isVisible = true
            }
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
        itemTouchHelper.attachToRecyclerView(binding.recycler)
    }

    private fun createItemTouchHelper() {
        val itemHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(recyclerView: RecyclerView,viewHolder: RecyclerView.ViewHolder,target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = noteAdapter.differAsync.currentList[viewHolder.adapterPosition]
                viewModel.triggerEvent(NoteListEvents.RemoveNoteFromCache(note))
                binding.confirmUndoGroup.isVisible = true
                binding.fab.isGone = true
            }
        }
        itemTouchHelper = ItemTouchHelper(itemHelper)
    }


    private fun removeDeleteNoteCallback(){
        itemTouchHelper.attachToRecyclerView(null)
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



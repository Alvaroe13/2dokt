package com.alvaro.ui_note.notelist

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvaro.ui_note.R
import com.alvaro.ui_note.databinding.FragmentNoteListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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

        /*deleteNote()*/

        initRecyclerView()
        fabClicked()
        subscribeObservers()
    }

    private fun subscribeObservers() {

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


    /*private fun getAllNotes() {
        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer { response ->
            noteList = response
            println("MainFragment, response size -> = ${response.size}")
            noteAdapter.differAsync.submitList(response)
        })
    }*/

    /*private fun deleteNote() {

        itemHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

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
                viewModel.deleteNote(note)
                noteAdapter.differAsync.submitList(noteList)
                println("MainFragment, onSwiped called!")
            }
        }

        val swipeToDelete = ItemTouchHelper(itemHelper as ItemTouchHelper.SimpleCallback)
        swipeToDelete.attachToRecyclerView(recycler)
    }*/

    override fun itemClick(noteId: Int) {
        val bundle = bundleOf( NOTE_ID_KEY to noteId)
        findNavController().navigate(R.id.action_NoteListFragment_to_noteDetailFragment, bundle)
    }

}



package com.example.notes.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.model.Note
import com.example.notes.ui.MainActivity
import com.example.notes.ui.adapter.NoteAdapter
import com.example.notes.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_main.*

private const val TAG = "MainFragment"

class MainFragment : Fragment(R.layout.fragment_main), NoteAdapter.ClickHandler {

    private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private var itemHelper: Any? = null
    private lateinit var noteList: List<Note>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()
        getAllNotes()
        fabClicked()
        deleteNote()
    }

    private fun initRecyclerView() {
        noteAdapter = NoteAdapter(this)
        recycler.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun fabClicked() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_noteRoomFragment)
        }
    }

    private fun getAllNotes() {
        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer { response ->
            noteList = response
            println("MainFragment, response size -> = ${response.size}")
            noteAdapter.differAsync.submitList(response)
        })

    }

    private fun deleteNote() {

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
    }

    override fun itemClick(position: Int) {
        val note = noteList[position]
        Log.d(TAG, "itemClick: priority = ${note.priority} ")
        val bundle = bundleOf(
            "id" to note.id,
            "title" to note.title,
            "content" to note.content,
            "priority" to note.priority
        )
        findNavController().navigate(R.id.action_mainFragment_to_noteRoomFragment, bundle)
    }

}



package com.example.notes.ui.fragments

import android.os.Bundle
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

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private var itemHelper: Any? = null
    private lateinit var noteList: List<Note>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("DEBUGGING, onViewCreated called!")
        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()
        getAllNotes()
        fabClicked()
        clickNote()
        deleteNote()
    }

    private fun initRecyclerView(){
        noteAdapter = NoteAdapter()
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

    private fun clickNote(){
        noteAdapter.setOnNoteClickListener {
            println("MainFragment, note clicked = ${it.title} with content = ${it.content}")
            val bundle = bundleOf(
                "id" to it.id,
                "title" to it.title,
                "content" to it.content,
                "priority" to it.priority
            )
            findNavController().navigate(R.id.action_mainFragment_to_noteRoomFragment , bundle)
        }
    }


    private fun deleteNote(){

        itemHelper = object : ItemTouchHelper.SimpleCallback(0 , ItemTouchHelper.LEFT){

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

}



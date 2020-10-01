package com.example.notes.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.model.Note
import com.example.notes.ui.MainActivity
import com.example.notes.viewModel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note_room.*

class NoteRoomFragment: Fragment(R.layout.fragment_note_room) {

    private var toolbar: androidx.appcompat.widget.Toolbar? = null
    private var toolbarTitle: TextView? = null
    private var spinner : Spinner? = null
    private lateinit var viewSnack :View
    private lateinit var viewModel: NoteViewModel

    private lateinit var title: String
    private lateinit var content: String
    private var priority: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        viewSnack = view
        setHasOptionsMenu(true)
        toolbar()
        setPriority()
    }

    private fun toolbar(){
        toolbar = activity?.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarTitle = activity?.tbTitle
        toolbarTitle?.text = "Note"

        spinner = activity?.spinner
        spinner?.visibility = View.VISIBLE
    }

    private fun setPriority() {
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long){
                when (position) {
                    0 -> priority = 1
                    1 -> priority = 2
                    2 -> priority = 3
                    3 -> priority = 4
                    4 -> priority = 5
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.saveNote){
            saveNote(priority)
            Snackbar.make(viewSnack, "Note saved", Snackbar.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote(priority : Int){
        title = noteTitle.text.toString()
        content = noteContent.text.toString()
        val note = Note(title, content, priority, 0)
        viewModel.insertNote(note)
        println("DEBUGGING, NoteRoomFragment, note title= ${note.title} + content =${note.content} + priority=${note.priority}")
    }

    override fun onPause() {
        super.onPause()
        toolbarTitle!!.text = getString(R.string.app_name)
        spinner?.visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}
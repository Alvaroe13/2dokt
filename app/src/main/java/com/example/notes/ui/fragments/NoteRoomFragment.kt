package com.example.notes.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.model.Note
import com.example.notes.ui.MainActivity
import com.example.notes.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note_room.*

private const val TAG = "NoteRoomFragment"

class NoteRoomFragment: Fragment(R.layout.fragment_note_room) {

    private lateinit var title: String
    private lateinit var content: String
    private var priority: Int = 1
    private var toolbar: androidx.appcompat.widget.Toolbar? = null
    private var toolbarTitle: TextView? = null
    private var spinner : Spinner? = null
    private lateinit var viewSnack :View
    private lateinit var viewModel: NoteViewModel

    private var incomingTitle: String? = null
    private var incomingContent: String? = null
    private var incomingPriority: String? = null
    private var isUpdating: Boolean = false
    private var incomingID = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        viewSnack = view
        setHasOptionsMenu(true)
        toolbar()
        setPriority()
        incomingBundle()
    }

    private fun incomingBundle(){
        if (arguments != null){
            isUpdating = true
            Log.d(TAG, "incomingBundle: bundle not null")

            incomingID = arguments?.getInt("id")!!
            incomingTitle = arguments?.getString("title")
            incomingContent = arguments?.getString("content")
            incomingPriority = arguments?.getString("priority")
            Log.d(TAG, "incomingBundle:  ID = $incomingID")

            noteTitle.setText(incomingTitle)
            noteContent.setText(incomingContent)
        }else{
            Log.d(TAG, "incomingBundle: bundle NULL")
            isUpdating = false
        }
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
            if (isUpdating){
                Log.d(TAG, "onOptionsItemSelected: isUpdating == true")
                updateNote()
            }else{
                Log.d(TAG, "onOptionsItemSelected: isUpdating == false ")
                saveNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateNote() {
        title = noteTitle.text.toString()
        content = noteContent.text.toString()
        val updateNote = Note(incomingID,title, content, priority, 0)
        viewModel.updateNote(updateNote)
        Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
    }

    private fun saveNote(){
        title = noteTitle.text.toString()
        content = noteContent.text.toString()
        val note = Note(0, title, content, priority, 0)
        if (title.isNotEmpty() && content.isNotEmpty()){
            viewModel.insertNote(note)
            Toast.makeText(context, "Note saved", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "No field can be empty" , Toast.LENGTH_SHORT).show()
        }

    }

    override fun onPause() {
        super.onPause()
        toolbarTitle!!.text = getString(R.string.app_name)
        spinner?.visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}
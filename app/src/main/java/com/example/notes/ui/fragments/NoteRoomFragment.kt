package com.example.notes.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class NoteRoomFragment: Fragment(R.layout.fragment_note_room) {

    private var toolbar: androidx.appcompat.widget.Toolbar? = null
    private var toolbarTitle: TextView? = null
    private var spinner : Spinner? = null
    private lateinit var menuOption : Menu
    private lateinit var viewSnack :View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewSnack = view
        setHasOptionsMenu(true)
        toolbar()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        menuOption = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.saveNote){
            Snackbar.make(viewSnack, "Note saved", Snackbar.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        toolbarTitle!!.text = getString(R.string.app_name)
        spinner?.visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}
package com.example.notes.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notes.R
import kotlinx.android.synthetic.main.activity_main.*

class NoteRoomFragment: Fragment(R.layout.fragment_note_room) {

    private var toolbar: androidx.appcompat.widget.Toolbar? = null
    private var toolbarTitle: TextView? = null
    private var spinner : Spinner? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onPause() {
        super.onPause()
        toolbarTitle!!.text = getString(R.string.app_name)
        spinner?.visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}
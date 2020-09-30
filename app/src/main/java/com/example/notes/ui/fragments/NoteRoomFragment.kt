package com.example.notes.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.notes.R
import kotlinx.android.synthetic.main.activity_main.*

class NoteRoomFragment: Fragment(R.layout.fragment_note_room) {

    var toolbarTitle: TextView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarTitle = activity?.tbTitle
        toolbarTitle?.text = "Note"
    }

    override fun onPause() {
        super.onPause()
        toolbarTitle!!.text = getString(R.string.app_name)
    }

}
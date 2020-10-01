package com.example.notes.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.ui.MainActivity
import com.example.notes.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var viewModel: NoteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("DEBUGGING, onViewCreated called!")
        viewModel = (activity as MainActivity).viewModel
        getAllNotes()
        fabClicked()
    }

    private fun fabClicked() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_noteRoomFragment)
        }
    }

    private fun getAllNotes() {
        viewModel.notes?.observe(viewLifecycleOwner, Observer {
        })

    }
}
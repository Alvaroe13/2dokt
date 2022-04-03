package com.alvaro.ui_note.notedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alvaro.note_domain.model.Note
import com.alvaro.ui_note.R
import com.alvaro.ui_note.databinding.FragmentNoteDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {

    companion object{
        private const val TAG = "NoteRoomFragment"
    }

    lateinit var binding : FragmentNoteDetailBinding
    private val viewModel : NoteDetailViewModel by viewModels()
    private var isUpdatingNote = false


    //private var timeStamp = DateGenerator.getDate()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNoteDetailBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

       /* spinnerListener() */
        subscribeObservers()
    }

    private fun subscribeObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                state.note?.let {
                    isUpdatingNote = true
                    displayDetails(it)
                }
                println("${TAG} triggered ${state}, isUpdatingNote = ${isUpdatingNote}")
            }
        }

    }

    private fun displayDetails(note : Note){
        binding.apply{
            noteTitle.setText(note.title)
            noteContent.setText(note.content)
            spinnerPriority.setSelection(note.priority)
        }
    }


    /*
    private fun spinnerListener() {
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long){
                //this value is set only if user touches spinner.
                priority = position + 1
            }
        }

    }

    private fun updateNote() {
        title = noteTitle.text?.trim().toString()
        content = noteContent.text?.trim().toString()
        val updateNote = Note(incomingID, title, content, priority, timeStamp)
        viewModel.updateNote(updateNote)
        goToMain()
        hideKeyboard()
        Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
    }

    private fun saveNote() {
        title = noteTitle.text?.trim().toString()
        content = noteContent.text?.trim().toString()
        val note = Note(0, title, content, priority, timeStamp)
        if (title.isNotEmpty() && content.isNotEmpty()) {
            viewModel.insertNote(note)
            goToMain()
            hideKeyboard()
            Toast.makeText(activity, "Note saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "No field can be empty", Toast.LENGTH_SHORT).show()
        }
    }
    */

}
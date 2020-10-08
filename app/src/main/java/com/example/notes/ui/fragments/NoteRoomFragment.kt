package com.example.notes.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.notes.R
import com.example.notes.model.Note
import com.example.notes.ui.MainActivity
import com.example.notes.util.Constants.SPINNER_DEFAULT_VALUE
import com.example.notes.util.DateGenerator
import com.example.notes.util.NavHelper
import com.example.notes.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note_room.*
import java.lang.Exception

private const val TAG = "NoteRoomFragment"

class NoteRoomFragment : Fragment(R.layout.fragment_note_room) {

   //ui
    private var toolbar: androidx.appcompat.widget.Toolbar? = null
    private var toolbarTitle: TextView? = null
    private var spinner: Spinner? = null
    private lateinit var viewModel: NoteViewModel
    private lateinit var layout :View
    //vars
    private lateinit var title: String
    private lateinit var content: String
    private var priority: Int = 1
    //bundle var
    private var incomingTitle: String? = null
    private var incomingContent: String? = null
    private var incomingPriority: Int = 0
    private var isUpdating: Boolean = false
    private var incomingID = 0
    private var timeStamp = DateGenerator.getDate()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout = view
        viewModel = (activity as MainActivity).viewModel
        setHasOptionsMenu(true)
        toolbar()
        spinnerListener()
        incomingBundle()
        setSpinnerPosition()
        noteTitle.setText(incomingTitle)
        noteContent.setText(incomingContent)
    }

    private fun incomingBundle() {
        if (arguments != null) {
            Log.d(TAG, "incomingBundle: bundle not null")
            //needed to keep track if toolbar icon should act as insert or update in DAO
            isUpdating = true
            incomingID = arguments?.getInt("id")!!
            incomingTitle = arguments?.getString("title").toString()
            incomingContent = arguments?.getString("content").toString()
            incomingPriority = requireArguments().getInt("priority")
        } else {
            Log.d(TAG, "incomingBundle: bundle NULL")
            //needed to keep track if toolbar icon should act as insert or update in DAO
            isUpdating = false
        }
    }

    private fun toolbar() {
        toolbar = activity?.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarTitle = activity?.tbTitle
        toolbarTitle?.text = "Note"

        spinner = activity?.spinner
        spinner?.visibility = View.VISIBLE
    }

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
        title = noteTitle.text.toString()
        content = noteContent.text.toString()
        val updateNote = Note(incomingID, title, content, priority, timeStamp)
        viewModel.updateNote(updateNote)
        goToMain()
        hideKeyboard()
        Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
    }

    private fun saveNote() {
        title = noteTitle.text.toString()
        content = noteContent.text.toString()
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

    private fun setSpinnerPosition() {
        Log.d(TAG, "setSpinnerPosition: incomingPriority = $incomingPriority")
        if (incomingPriority == 0){
            spinner?.setSelection(SPINNER_DEFAULT_VALUE)
        }else{
            spinner?.setSelection(incomingPriority - 1)
        }
    }

    private fun goToMain(){
        NavHelper.navigateWithoutStack(layout, R.id.action_noteRoomFragment_to_mainFragment2, null )
    }

    private fun hideKeyboard(){
        try {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    //-------------------------------- override functions -----------------------------------------//

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.saveNote) {
            if (isUpdating) {
                Log.d(TAG, "onOptionsItemSelected: isUpdating == true")
                updateNote()
            } else {
                Log.d(TAG, "onOptionsItemSelected: isUpdating == false ")
                saveNote()
            }
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
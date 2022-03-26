package com.example.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    /*private lateinit var vMFactory : com.alvaro.ui_note.notedetail.ViewModelFactory
    lateinit var viewModel : com.alvaro.ui_note.notedetail.NoteViewModel*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // initViewModel()
    }

    /*private fun initViewModel() {
        val repo =
            com.alvaro.note_datasource.Repository(com.alvaro.note_datasource.NotesDatabase(this))
        vMFactory = com.alvaro.ui_note.notedetail.ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, vMFactory).get(com.alvaro.ui_note.notedetail.NoteViewModel::class.java)
    }

    //needed for toolbar backButton
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }*/
}
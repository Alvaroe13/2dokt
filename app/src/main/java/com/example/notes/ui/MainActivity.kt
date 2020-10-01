package com.example.notes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.R
import com.example.notes.cache.NotesDatabase
import com.example.notes.repositories.Repository
import com.example.notes.viewModel.NoteViewModel
import com.example.notes.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var vMFactory : ViewModelFactory
    lateinit var viewModel : NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
    }

    private fun initViewModel() {
        val repo = Repository(NotesDatabase(this))
        vMFactory = ViewModelFactory(repo)
        viewModel = ViewModelProvider(this, vMFactory).get(NoteViewModel::class.java)
    }

    //needed for toolbar backButton
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
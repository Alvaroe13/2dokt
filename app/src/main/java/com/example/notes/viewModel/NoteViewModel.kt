package com.example.notes.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.model.Note
import com.example.notes.repositories.Repository
import kotlinx.coroutines.launch

class NoteViewModel(
    private var repository : Repository
) : ViewModel() {

    val notes : MutableLiveData<List<Note>>? = null

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun deleteNote(note:Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun getAllNotes() = repository.getAllNotes()

}
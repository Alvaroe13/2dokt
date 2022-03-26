package com.alvaro.ui_notedetail

import androidx.lifecycle.ViewModel
import com.alvaro.note_datasource.Repository

class NoteViewModel(
    private var repository : Repository
) : ViewModel() {

   /* val notes : MutableLiveData<List<Note>>? = null

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun deleteNote(note:Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

    fun getAllNotes() = repository.getAllNotes()*/

}
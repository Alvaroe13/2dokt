package com.alvaro.ui_note.notedetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor()  : ViewModel() {

    //  private var repository : Repository

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
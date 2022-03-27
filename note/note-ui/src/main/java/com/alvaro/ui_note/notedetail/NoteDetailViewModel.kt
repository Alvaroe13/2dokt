package com.alvaro.ui_note.notedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.note_domain.interactors.InsertNote
import com.alvaro.note_domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val insertNote: InsertNote
)  : ViewModel() {


    val notes : MutableLiveData<List<Note>>? = null

    fun insertNote(note: Note) = viewModelScope.launch {
        insertNote.execute(note)
    }

    /*fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }*/


}
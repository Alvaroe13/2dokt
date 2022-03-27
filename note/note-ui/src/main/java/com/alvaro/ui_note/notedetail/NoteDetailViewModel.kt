package com.alvaro.ui_note.notedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.LoadingState
import com.alvaro.note_domain.interactors.InsertNote
import com.alvaro.note_domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val insertNote: InsertNote
) : ViewModel() {


    val notes = MutableLiveData<DataState<List<Note>>>(DataState.Loading(LoadingState.Idle))

    init {
        insertNote(Note.emptyNote())
    }

    fun insertNote(note: Note) {

        insertNote.execute(note).onEach { dataState ->
            notes.value = DataState.Loading(LoadingState.Loading)
            when (dataState) {
                is DataState.Data -> {
                    notes.value = DataState.Loading(LoadingState.Idle)
                }
                is DataState.Response -> {
                    notes.value = DataState.Loading(LoadingState.Idle)
                }
            }

        }.launchIn(viewModelScope)
    }

    /*fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }*/


}
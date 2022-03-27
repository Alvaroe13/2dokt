package com.alvaro.ui_note.notedetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.LoadingState
import com.alvaro.core.domain.UIComponent
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

    val notes: MutableLiveData<NoteDetailState> = MutableLiveData(NoteDetailState())
    val response: MutableLiveData<DataState.Response<UIComponent>> = MutableLiveData()

    init {
        triggerEvent(NoteDetailsEvents.InsertNote(Note.emptyNote()))
    }

    fun triggerEvent(event: NoteDetailsEvents) {

        notes.value = notes.value?.copy(loadingState = LoadingState.Loading)

        when (event) {
            is NoteDetailsEvents.InsertNote -> {
                insertNote(event.note)
            }
            is NoteDetailsEvents.UpdateNote -> {
            }
        }
    }

    private fun insertNote(note: Note) {

        insertNote.execute(note).onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    notes.value = notes.value?.copy(loadingState = LoadingState.Idle)
                }
                is DataState.Response -> {
                    notes.value = notes.value?.copy(loadingState = LoadingState.Idle)
                    response.value = response.value?.copy(uiComponent = dataState.uiComponent)
                }
            }

        }.launchIn(viewModelScope)
    }

    /*fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }*/


}
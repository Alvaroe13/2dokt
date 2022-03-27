package com.alvaro.ui_note.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.LoadingState
import com.alvaro.core.domain.UIComponent
import com.alvaro.core.util.Logger
import com.alvaro.note_domain.interactors.InsertNote
import com.alvaro.note_domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val insertNote: InsertNote,
    @Named("NoteDetailView") private val logger: Logger
) : ViewModel() {

    val state : LiveData<NoteDetailState> get() = _state
    private val _state: MutableLiveData<NoteDetailState> = MutableLiveData(NoteDetailState.build())

    val response : LiveData<DataState.Response<UIComponent>> get() = _response
    private val _response: MutableLiveData<DataState.Response<UIComponent>> = MutableLiveData()

    init {
        triggerEvent(NoteDetailsEvents.InsertNote(Note.emptyNote()))
    }

    fun triggerEvent(event: NoteDetailsEvents) {

        _state.value = _state.value?.copy(loadingState = LoadingState.Loading)

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
                    _state.value = _state.value?.copy(loadingState = LoadingState.Idle)
                }
                is DataState.Response -> {
                    _state.value = _state.value?.copy(loadingState = LoadingState.Idle)
                    when (dataState.uiComponent) {
                        is UIComponent.None -> {
                            logger.log((dataState.uiComponent as UIComponent.None).message)
                        }
                        else -> {
                            _response.value = _response.value?.copy(uiComponent = dataState.uiComponent)
                        }
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    /*fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }*/


}
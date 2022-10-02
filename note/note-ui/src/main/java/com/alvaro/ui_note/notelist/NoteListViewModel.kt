package com.alvaro.ui_note.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.LoadingState
import com.alvaro.core.domain.UIComponent
import com.alvaro.core.util.DispatcherProvider
import com.alvaro.core.util.Logger
import com.alvaro.note_domain.model.Note
import com.alvaro.note_interactors.notelist.DeleteNote
import com.alvaro.note_interactors.notelist.GetNotes
import com.alvaro.note_interactors.notelist.RemoveNoteFromCacheUseCase
import com.alvaro.ui_note.notelist.viewstate.DeletionState
import com.alvaro.ui_note.notelist.viewstate.NoteListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotes: GetNotes,
    private val deleteNote: DeleteNote,
    private val removeNoteFromCacheUseCase: RemoveNoteFromCacheUseCase,
    private val dispatcherProvider: DispatcherProvider,
    @Named("NoteListView") private val logger: Logger,
) : ViewModel() {

    val state: StateFlow<NoteListState> get() = _state
    private val _state: MutableStateFlow<NoteListState> = MutableStateFlow(NoteListState.build())

    val response: SharedFlow<UIComponent> get() = _response
    private val _response: MutableSharedFlow<UIComponent> = MutableSharedFlow()

    var didScreenRotated = false
    var noteForDeletion: Note? = null

    init {
        triggerEvent(NoteListEvents.GetNotes)
    }


    fun triggerEvent(event: NoteListEvents) {

        _state.value = _state.value.copy(loadingState = LoadingState.Loading)

        when (event) {
            is NoteListEvents.GetNotes -> {
                retrieveNoteList()
            }
            is NoteListEvents.RemoveNoteFromCache -> {
                removeNoteFromCache(event.note)
            }
            is NoteListEvents.ConfirmDeletion -> {
                deleteNote(event.note)
            }
            is NoteListEvents.UndoDeletion -> {
                undoDeletion()
            }
        }
    }

    private fun retrieveNoteList() {
       viewModelScope.launch(dispatcherProvider.io()) {

            getNotes.execute().collect { dataState ->

                withContext(dispatcherProvider.main()){
                    when (dataState) {
                        is DataState.Data -> {
                           _state.value =  _state.value.copy(
                                noteList = dataState.data ?: emptyList(),
                                loadingState = LoadingState.Idle,
                                deletionState = DeletionState.Idle
                           )
                        }
                        is DataState.Response -> {
                            when (dataState.uiComponent) {
                                is UIComponent.None -> {
                                    logger.log((dataState.uiComponent as UIComponent.None).message)
                                }
                                else -> {
                                    _response.emit(dataState.uiComponent)
                                }
                            }
                        }
                    }
                }

            }

        }
    }

    private fun deleteNote(note: Note){
        noteForDeletion = null

        viewModelScope.launch(dispatcherProvider.io()) {

            deleteNote.execute(note).collect { dataState ->

                when (dataState) {
                    is DataState.Data -> {
                        triggerEvent(NoteListEvents.GetNotes)
                    }
                    is DataState.Response -> {
                        when (dataState.uiComponent) {
                            is UIComponent.None -> {
                                logger.log((dataState.uiComponent as UIComponent.None).message)
                            }
                            else -> {
                                _response.emit(dataState.uiComponent)
                            }
                        }
                    }
                }

            }

        }
    }

    private fun removeNoteFromCache(note: Note) {
        noteForDeletion = note

        viewModelScope.launch(dispatcherProvider.main()) {

            removeNoteFromCacheUseCase(note).also { dataState ->

                when (dataState) {
                    is DataState.Data -> {
                        _state.value = _state.value.copy(
                            noteList = dataState.data ?: emptyList(),
                            loadingState = LoadingState.Idle,
                            deletionState = DeletionState.OnDeletion
                        )
                    }
                    is DataState.Response -> {
                        when (dataState.uiComponent) {
                            is UIComponent.None -> {
                                logger.log((dataState.uiComponent as UIComponent.None).message)
                            }
                            else -> {
                                _response.emit(dataState.uiComponent)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun undoDeletion(){
        noteForDeletion = null
        triggerEvent(NoteListEvents.GetNotes)
    }

}
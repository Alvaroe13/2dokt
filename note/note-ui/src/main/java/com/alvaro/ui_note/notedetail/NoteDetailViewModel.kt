package com.alvaro.ui_note.notedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.LoadingState
import com.alvaro.core.domain.UIComponent
import com.alvaro.core.util.Logger
import com.alvaro.note_domain.interactors.notedetailview.GetNoteById
import com.alvaro.note_domain.interactors.notedetailview.InsertNote
import com.alvaro.note_domain.interactors.notedetailview.UpdateNote
import com.alvaro.note_domain.model.Note
import com.alvaro.ui_note.notelist.NoteListFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val insertNote: InsertNote,
    private val getNoteById: GetNoteById,
    private val updateNote: UpdateNote,
    @Named("NoteDetailView") private val logger: Logger,
    @Named("IO") private val io: CoroutineDispatcher,
    @Named("Main") private val main: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: StateFlow<NoteDetailState> get() = _state
    private val _state: MutableStateFlow<NoteDetailState> = MutableStateFlow(NoteDetailState.build())

    val response: SharedFlow<UIComponent> get() = _response
    private val _response: MutableSharedFlow<UIComponent> = MutableSharedFlow()

    init {
        val noteId = savedStateHandle.get<String>(NoteListFragment.NOTE_ID_KEY) ?: ""
        if (noteId.isNotBlank()){
            triggerEvent(NoteDetailsEvents.GetNoteById(noteId))
        }
    }

    fun triggerEvent(event: NoteDetailsEvents) {

        _state.value = _state.value.copy(loadingState = LoadingState.Loading)

        when (event) {
            is NoteDetailsEvents.InsertNote -> {
                insertNote(event.note)
            }
            is NoteDetailsEvents.UpdateNote -> {
                updateNote(event.note)
            }
            is NoteDetailsEvents.GetNoteById -> {
                getNoteById(event.noteId)
            }
        }
    }

    private fun insertNote(note: Note) {

        viewModelScope.launch(io) {
            insertNote.execute(note).collect { dataState ->
                withContext(main) {

                    when (dataState) {
                        is DataState.Data -> {
                            _state.value = _state.value.copy(
                                loadingState = LoadingState.Idle,
                                saveNoteType = SaveNoteType.InsertNote
                            )
                        }
                        is DataState.Response -> {
                            _state.value = _state.value.copy(loadingState = LoadingState.Idle)
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

    private fun getNoteById(noteId: String) {
        viewModelScope.launch(io) {
            getNoteById.execute(noteId).collect { dataState ->
                withContext(main) {

                    when (dataState) {
                        is DataState.Data -> {
                            _state.value = _state.value.copy(
                                note = dataState.data,
                                loadingState = LoadingState.Idle
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

    private fun updateNote(note: Note) {
        viewModelScope.launch(io) {
            updateNote.execute(note, this).collect { dataState ->
                withContext(main) {

                    when (dataState) {
                        is DataState.Data -> {
                            _state.value = _state.value.copy(
                                loadingState = LoadingState.Idle,
                                saveNoteType = SaveNoteType.UpdateNote
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


}
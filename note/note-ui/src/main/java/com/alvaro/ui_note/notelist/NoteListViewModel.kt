package com.alvaro.ui_note.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.LoadingState
import com.alvaro.core.domain.UIComponent
import com.alvaro.core.util.Logger
import com.alvaro.note_domain.interactors.GetNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotes: GetNotes,
    @Named("NoteListView") private val logger: Logger,
    @Named("IO") private val io: CoroutineDispatcher,
    @Named("Main") private val main: CoroutineDispatcher
) : ViewModel() {

    val state: StateFlow<NoteListState> get() = _state
    private val _state: MutableStateFlow<NoteListState> = MutableStateFlow(NoteListState.build())

    val response: SharedFlow<UIComponent> get() = _response
    private val _response: MutableSharedFlow<UIComponent> = MutableSharedFlow()

    init {
        triggerEvent(NoteListEvents.GetNotes)
    }


    fun triggerEvent(event: NoteListEvents) {

        _state.value = _state.value.copy(loadingState = LoadingState.Loading)

        when (event) {
            is NoteListEvents.GetNotes -> {
                retrieveNoteList()
            }
        }
    }

    private fun retrieveNoteList() {
       viewModelScope.launch(io) {

            getNotes.execute().collect { dataState ->

                withContext(main){
                    when (dataState) {
                        is DataState.Data -> {
                            _state.value = _state.value.copy(
                                noteList = dataState.data,
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
}
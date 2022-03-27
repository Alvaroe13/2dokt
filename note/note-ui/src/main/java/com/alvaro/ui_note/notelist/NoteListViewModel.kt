package com.alvaro.ui_note.notelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.LoadingState
import com.alvaro.core.domain.UIComponent
import com.alvaro.core.util.Logger
import com.alvaro.note_domain.interactors.GetNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getNotes: GetNotes,
    @Named("NoteListView") private val logger: Logger
) : ViewModel() {

    val state: LiveData<NoteListState> get() = _state
    private val _state: MutableLiveData<NoteListState> = MutableLiveData(NoteListState.build())

    val response: LiveData<DataState.Response<UIComponent>> get() = _response
    private val _response: MutableLiveData<DataState.Response<UIComponent>> = MutableLiveData()

    init {
        triggerEvent(NoteListEvents.GetNotes)
    }


    fun triggerEvent(event: NoteListEvents) {

        _state.value = _state.value?.copy(loadingState = LoadingState.Loading)

        when (event) {
            is NoteListEvents.GetNotes -> {
                retrieveNoteList()
            }
        }
    }

    private fun retrieveNoteList() {
        getNotes.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Data -> {
                    println("NoteListViewModel data")
                    _state.value = _state.value?.copy(
                        noteList = dataState.data,
                        loadingState = LoadingState.Idle
                    )
                }
                is DataState.Response -> {
                    println("NoteListViewModel reponse")
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
}
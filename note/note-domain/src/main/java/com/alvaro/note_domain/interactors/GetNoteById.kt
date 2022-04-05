package com.alvaro.note_domain.interactors

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNoteById(
    private val noteRepository: NoteRepository
) {

    fun execute(noteId: String): Flow<DataState<Note>> = flow{

        try {
            emit( DataState.Data( data = noteRepository.getNoteById(noteId)))
        }catch (e :Exception){
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Toast(
                        message = e.localizedMessage ?: "Error retrieving notes from cache"
                    )
                )
            )
        }
    }
}
package com.alvaro.note_interactors.notedetail

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNoteById(
    private val noteRepository: NoteRepository
) {

    fun execute(
        noteId: String,
        forceExceptionForTesting: Boolean = false
    ): Flow<DataState<Note>> = flow{

        try {
            emit( DataState.Data( data = noteRepository.getNoteById(noteId, forceExceptionForTesting)))
        }catch (e :Exception){
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Toast(
                        message = ERROR_MSG
                    )
                )
            )
        }
    }

    companion object{
        const val ERROR_MSG = "Error retrieving notes from cache"
    }
}
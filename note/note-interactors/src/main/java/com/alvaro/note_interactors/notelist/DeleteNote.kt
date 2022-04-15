package com.alvaro.note_interactors.notelist

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteNote(private val noteRepository: NoteRepository) {


    fun execute(
        note: Note,
        forceExceptionForTesting: Boolean = false
    ): Flow<DataState<Int>> = flow {


        try {
            emit( DataState.Data(data = noteRepository.deleteNote(note, forceExceptionForTesting)))
        } catch (e: Exception) {
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Toast(
                        message = ERROR_DELETING_NOTE
                    )
                )
            )
        }

    }

    companion object {
        const val ERROR_DELETING_NOTE = "Error deleting note from cache"
    }

}
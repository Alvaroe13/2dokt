package com.alvaro.note_interactors.notelist

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteNote(private val noteRepository: NoteRepository) {


    fun execute(
        note: Note,
        scope: CoroutineScope
    ): Flow<DataState<List<Note>>> = flow {


        val result = scope.async {
            try {
                noteRepository.deleteNote(note)
            } catch (e: Exception) {
                e
            }
        }.await()

        if (result is Exception){
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Toast(
                        message = ERROR_DELETING_NOTE
                    )
                )
            )
        }


        try {
            val notes = noteRepository.getAllNotes()
            emit(DataState.Data(data = notes))
        } catch (e: Exception) {
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Error retrieving remaining notes from cache",
                        message = e.localizedMessage ?: "Unknown error"
                    )
                )
            )
        }

    }

    companion object {
        const val ERROR_DELETING_NOTE = "Error deleting note from cache"
    }

}
package com.alvaro.note_domain.interactors.notedetailview

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.core.util.TimeStampGenerator
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertNote(
    private val noteRepository: NoteRepository,
    private val timeStampGenerator: TimeStampGenerator
    ) {

    fun execute(note: Note): Flow<DataState<Long>> = flow {

        if (!isValid(note)) {
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Toast(
                        message = "Title and content must be not blank"
                    )
                )
            )
            return@flow
        }

        note.timeStamp = timeStampGenerator.getDate() ?: "-"

        try {
            emit(DataState.Data(data = noteRepository.insertNote(note)))
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Toast(message = "Note saved successfully")
                )
            )
        } catch (e: Exception) {
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Database Error",
                        message = e.message ?: "Unknown error"
                    )
                )
            )
        }

    }

    private fun isValid(note: Note): Boolean {
        return note.title.isNotBlank() && note.content.isNotBlank()
    }
}
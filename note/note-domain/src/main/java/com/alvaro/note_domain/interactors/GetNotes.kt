package com.alvaro.note_domain.interactors

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNotes(
    private val noteRepository: NoteRepository
) {

    fun execute(): Flow<DataState<List<Note>>> = flow {

        try {
            val notes = noteRepository.getAllNotes()
            emit(DataState.Data(data = notes ))
        } catch (e: Exception) {
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Error retrieving notes from cache",
                        message = e.localizedMessage ?: "Unknown error"
                    )
                )
            )
        }
    }

}
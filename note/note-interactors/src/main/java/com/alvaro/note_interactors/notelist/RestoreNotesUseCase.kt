package com.alvaro.note_interactors.notelist

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestoreNotesUseCase(private val noteRepository: NoteRepository) {

    suspend operator fun invoke(forceExceptionForTesting: Boolean = false): Flow<DataState<List<Note>>> = flow {
        try {
            noteRepository.getCacheNotes().forEach {
                noteRepository.insertNote(it)
            }
            emit(DataState.Data(data = noteRepository.getAllNotes(forceExceptionForTesting)))
        } catch (e: Exception) {
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Error retrieving cached notes",
                        message = e.localizedMessage ?: "Unknown error"
                    )
                )
            )
        }
    }
}
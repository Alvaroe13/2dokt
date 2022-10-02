package com.alvaro.note_interactors.notelist

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository

class RemoveNoteFromCacheUseCase(private val noteRepository: NoteRepository) {

    operator fun invoke(note: Note): DataState<List<Note>> {
        return try {
            val notes = noteRepository.getCacheNotes().toMutableList()
            notes.remove(note)
            DataState.Data(data = notes)
        } catch (e: Exception) {
            DataState.Response(
                uiComponent = UIComponent.Dialog(
                    title = "Error removing note from cache",
                    message = e.localizedMessage ?: "Unknown error"
                )
            )
        }
    }
}
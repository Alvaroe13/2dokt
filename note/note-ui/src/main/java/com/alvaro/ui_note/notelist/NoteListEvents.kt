package com.alvaro.ui_note.notelist

import com.alvaro.note_domain.model.Note

sealed class NoteListEvents {
    object GetNotes : NoteListEvents()
    data class DeleteNote(val note: Note) : NoteListEvents()
}
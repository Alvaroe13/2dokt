package com.alvaro.ui_note.notelist

import com.alvaro.note_domain.model.Note

sealed class NoteListEvents {
    object GetNotes : NoteListEvents()
    data class RemoveNoteFromCache(val note: Note) : NoteListEvents()
    data class ConfirmDeletion(val note: Note) : NoteListEvents()
    object UndoDeletion : NoteListEvents()
}
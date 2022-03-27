package com.alvaro.ui_note.notedetail

import com.alvaro.note_domain.model.Note

sealed class NoteDetailsEvents{
    data class InsertNote(val note: Note): NoteDetailsEvents()
    data class UpdateNote(val note: Note): NoteDetailsEvents()
}

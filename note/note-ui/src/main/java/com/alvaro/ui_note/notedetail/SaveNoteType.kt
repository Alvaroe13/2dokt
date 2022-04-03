package com.alvaro.ui_note.notedetail

sealed class SaveNoteType {
    object InsertNote: SaveNoteType()
    object UpdateNote: SaveNoteType()
    object None: SaveNoteType()
}
package com.alvaro.note_domain.repository

import com.alvaro.note_domain.model.Note

interface NoteRepository {

    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun getAllNotes(): List<Note>

}
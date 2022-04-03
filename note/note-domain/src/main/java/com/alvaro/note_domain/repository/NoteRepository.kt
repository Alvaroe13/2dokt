package com.alvaro.note_domain.repository

import com.alvaro.note_domain.model.Note

interface NoteRepository {

    suspend fun insertNote(note: Note) : Long
    suspend fun updateNote(note: Note) : Int
    suspend fun deleteNote(note: Note) : Int
    suspend fun getAllNotes(): List<Note>
    suspend fun getNoteById(noteId: Int) : Note

}
package com.example.notes.repositories

import com.example.notes.cache.NotesDatabase
import com.example.notes.model.Note

class Repository (private var database: NotesDatabase) {

    suspend fun insertNote(note : Note) = database.getDao().insertNote(note)

    suspend fun update(note: Note) = database.getDao().updateNote(note)

    suspend fun deleteNote(note: Note) = database.getDao().deleteNote(note)

    fun getAllNotes() = database.getDao().getNotesByPriorityDesc()
}
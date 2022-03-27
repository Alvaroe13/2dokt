package com.alvaro.note_datasource

import com.alvaro.note_datasource.cache.NotesDatabase
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository

class NoteRepositoryImpl (
    private var database: NotesDatabase
) : NoteRepository {

    override suspend fun insertNote(note : Note) {
        database.getDao().insertNote(note)
    }

    override suspend fun updateNote(note: Note) {
        database.getDao().updateNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        database.getDao().deleteNote(note)
    }

    override suspend fun getAllNotes(): List<Note> {
        return database.getDao().getNotesByPriorityDesc()
    }

}
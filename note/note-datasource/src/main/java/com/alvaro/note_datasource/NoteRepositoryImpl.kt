package com.alvaro.note_datasource

import com.alvaro.note_datasource.cache.NotesDatabase
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository

class NoteRepositoryImpl (
    private val database: NotesDatabase,
    private val noteMapper: NoteMapper
) : NoteRepository {

    override suspend fun insertNote(note : Note, forceExceptionForTesting: Boolean) : Long {
        return database.getDao().insertNote(noteMapper.mapFrom(note))
    }

    override suspend fun updateNote(note: Note, forceExceptionForTesting: Boolean): Int {
        return database.getDao().updateNote(noteMapper.mapFrom(note))
    }

    override suspend fun deleteNote(note: Note, forceExceptionForTesting: Boolean) : Int {
        return database.getDao().deleteNote(noteMapper.mapFrom(note))
    }

    override suspend fun getAllNotes(forceExceptionForTesting: Boolean): List<Note> {
        return database.getDao().getNotesByPriorityDesc().map {
            noteMapper.mapTo(it)
        }
    }

    override suspend fun getNoteById(noteId: String, forceExceptionForTesting: Boolean): Note {
        return noteMapper.mapTo( database.getDao().getNoteById(noteId) )
    }

}
package com.alvaro.note_datasource_test.data

import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository

class NoteRepositoryTestImpl(
    private val notesDatabaseFake: NoteDatabaseFake
) : NoteRepository {

    private val notesCachedDatabase: MutableList<Note> = mutableListOf()

    override suspend fun insertNote(note: Note, forceExceptionForTesting: Boolean): Long {
        return notesDatabaseFake.insertNote(note, forceExceptionForTesting)
    }

    override suspend fun updateNote(note: Note, forceExceptionForTesting: Boolean): Int {
        return notesDatabaseFake.updateNote(note, forceExceptionForTesting)
    }

    override suspend fun deleteNote(note: Note, forceExceptionForTesting: Boolean): Int {
        return notesDatabaseFake.deleteNote(note, forceExceptionForTesting)
    }

    override suspend fun getAllNotes(forceExceptionForTesting: Boolean): List<Note> {
        return notesDatabaseFake.getAllNotes(forceExceptionForTesting)
    }

    override suspend fun getNoteById(noteId: String, forceExceptionForTesting: Boolean): Note {
        return notesDatabaseFake.getNoteById(noteId, forceExceptionForTesting)
    }

    override suspend fun getCacheNotes(forceExceptionForTesting: Boolean): List<Note> {
        return notesCachedDatabase
    }
}
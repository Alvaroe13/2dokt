package com.alvaro.note_datasource_test.data

import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository

class NoteRepositoryTestImpl(
    private val notesDatabaseFake: NoteDatabaseFake
) : NoteRepository {

    override suspend fun insertNote(note: Note): Long {
        return notesDatabaseFake.insertNote(note)
    }

    override suspend fun updateNote(note: Note): Int {
        return notesDatabaseFake.updateNote(note)
    }

    override suspend fun deleteNote(note: Note): Int {
        return notesDatabaseFake.deleteNote(note)
    }

    override suspend fun getAllNotes(): List<Note> {
       return notesDatabaseFake.getAllNotes()
    }

    override suspend fun getNoteById(noteId: String): Note {
        return notesDatabaseFake.getNoteById(noteId)
    }
}
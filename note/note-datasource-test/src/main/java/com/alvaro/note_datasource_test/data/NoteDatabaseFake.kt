package com.alvaro.note_datasource_test.data

import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository

class NoteDatabaseFake(
    private val noteFactory : NoteFactory
) : NoteRepository {

    val notesDatabase: MutableList<Note> = mutableListOf()

    override suspend fun insertNote(note: Note, forceExceptionForTesting: Boolean): Long {
        if (forceExceptionForTesting) forceException()
        notesDatabase.forEach {
            if (it.id == note.id) {
                notesDatabase.remove(it)
            }
        }
        notesDatabase.add(note)
        return -1
    }

    override suspend fun updateNote(note: Note, forceExceptionForTesting: Boolean): Int {
        if (forceExceptionForTesting) forceException()

        notesDatabase.removeIf { it.id == note.id }
        notesDatabase.add(note)
        return -1
    }

    override suspend fun deleteNote(note: Note, forceExceptionForTesting: Boolean): Int {
        if (forceExceptionForTesting) forceException()
         notesDatabase.removeIf { it.id == note.id }
        return -1
    }

    override suspend fun getAllNotes(forceExceptionForTesting: Boolean): List<Note> {
        if (forceExceptionForTesting) forceException()
        return notesDatabase.toList()
    }

    override suspend fun getNoteById(noteId: String, forceExceptionForTesting: Boolean): Note {
        if (forceExceptionForTesting) forceException()
        return notesDatabase.find { it.id == noteId }
            ?: throw NullPointerException("Note not found in db with id ${noteId}")
    }

    override suspend fun getCacheNotes(forceExceptionForTesting: Boolean): List<Note> {
        TODO("Not yet implemented")
    }

    //for test only
    fun addNote(){
        notesDatabase.add(noteFactory.buildNote())
    }

    fun addNotes(){
        noteFactory.buildNotes().forEach {
            notesDatabase.add(it)
        }
    }

    private fun forceException() {
        throw Exception(EXCEPTION_MESSAGE)
    }

    companion object{
        const val EXCEPTION_MESSAGE = "Error for testing has been triggered"
    }


}
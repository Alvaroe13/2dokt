package com.alvaro.note_datasource_test.data

import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository

class NoteDatabaseFake(
    private val noteFactory : NoteFactory
) : NoteRepository {

    val notesDatabase: MutableList<Note> = mutableListOf()

    override suspend fun insertNote(note: Note): Long {
        notesDatabase.forEach {
            if (it.id == note.id) {
                notesDatabase.remove(it)
            }
        }
        notesDatabase.add(note)
        return -1
    }

    override suspend fun updateNote(note: Note): Int {
        var wasNoteFound = false
        notesDatabase.forEach {
            if (it.id == note.id) {
                notesDatabase.remove(it)
                wasNoteFound = true
            }
        }
        if (wasNoteFound.not())
            throw NullPointerException("Updating note failed since it can't be updated a note that doesn't exist in database")

        notesDatabase.add(note)
        return -1
    }

    override suspend fun deleteNote(note: Note): Int {
        val wasDeleted = notesDatabase.removeIf { it.id == note.id }
        if (wasDeleted.not())
            throw NullPointerException("Note provided is not in database")
        return -1
    }

    override suspend fun getAllNotes(): List<Note> {
        return notesDatabase.toList()
    }

    override suspend fun getNoteById(noteId: String): Note {
        return notesDatabase.find { it.id == noteId }
            ?: throw NullPointerException("Note not found in db with id ${noteId}")
    }

    //for test only
    fun addNote(){
        notesDatabase.add(noteFactory.buildNote())
    }

    fun addNotes(){
        noteFactory.buildNotes().forEach {
            notesDatabase.add(noteFactory.buildNote())
        }
    }


}
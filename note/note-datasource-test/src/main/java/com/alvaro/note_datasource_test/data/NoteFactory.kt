package com.alvaro.note_datasource_test.data

import com.alvaro.note_domain.model.Note
import java.util.*

class NoteFactory {

    fun buildNote(): Note {
        return Note(
            id = UUID.randomUUID().toString(),
            title = UUID.randomUUID().toString(),
            content = "This is a test note created for the sake of testing our use case",
            priority = (0 until 5).random(),
            timeStamp = UUID.randomUUID().toString()
        )
    }

    fun buildNotes(): List<Note> {
        return mutableListOf(
            buildNote(),
            buildNote(),
            buildNote(),
            buildNote(),
            buildNote()
        )
    }

    fun buildNoteWithEmptyTitle(): Note {
        return Note(
            id = UUID.randomUUID().toString(),
            title = "",
            content = "This is some content",
            priority = (0 until 5).random(),
            timeStamp = UUID.randomUUID().toString()
        )
    }

    fun buildNoteWithEmptyContent(): Note {
        return Note(
            id = UUID.randomUUID().toString(),
            title = "This is some title",
            content = "",
            priority = (0 until 5).random(),
            timeStamp = UUID.randomUUID().toString()
        )
    }
}
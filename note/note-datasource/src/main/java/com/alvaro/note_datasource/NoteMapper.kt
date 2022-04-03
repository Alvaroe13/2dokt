package com.alvaro.note_datasource

import com.alvaro.note_datasource.cache.NoteEntity
import com.alvaro.note_domain.model.Note
import java.util.*

class NoteMapper {

    fun mapFrom(note: Note) : NoteEntity {
        return NoteEntity(
            id = note.id ?: UUID.randomUUID().toString().toInt(),
            title = note.title,
            content = note.content,
            priority = note.priority,
            timeStamp = note.timeStamp
        )
    }

    fun mapTo(noteEntity: NoteEntity): Note {
        return Note(
            id = noteEntity.id,
            title = noteEntity.title,
            content = noteEntity.content,
            priority = noteEntity.priority,
            timeStamp = noteEntity.timeStamp
        )
    }
}
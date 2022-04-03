package com.alvaro.note_datasource.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = NotesDatabase.DATABASE_NAME)
class NoteEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var title: String = "",
    var content: String = "",
    var priority: Int = 0,
    var timeStamp: String = ""
)
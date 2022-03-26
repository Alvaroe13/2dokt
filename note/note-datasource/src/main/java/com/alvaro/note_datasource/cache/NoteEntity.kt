package com.alvaro.note_datasource.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = DATABASE_NAME)
class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String = "",
    var content: String = "",
    var priority :Int,
    var timeStamp : String = ""
)
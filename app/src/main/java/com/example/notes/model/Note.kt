package com.example.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notes.util.Constants.DATABASE_NAME

@Entity( tableName = DATABASE_NAME)
class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String = "",
    var content: String = "",
    var priority :Int = 0,
    var timeStamp : String = ""
)
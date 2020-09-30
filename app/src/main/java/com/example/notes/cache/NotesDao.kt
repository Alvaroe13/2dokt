package com.example.notes.cache

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.model.Note

@Dao
interface NotesDao  {

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY timeStamp")
    suspend fun getNotesByTimeDes() : LiveData<List<Note>>
}
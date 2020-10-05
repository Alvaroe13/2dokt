package com.example.notes.cache

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.model.Note

@Dao
interface NotesDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY priority DESC")
    fun getNotesByPriorityDesc() : LiveData<List<Note>>
}
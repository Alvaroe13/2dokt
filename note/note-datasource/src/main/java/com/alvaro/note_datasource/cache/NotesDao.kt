package com.alvaro.note_datasource.cache

import androidx.room.*
import com.alvaro.note_domain.model.Note

@Dao
interface NotesDao  {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM noteDb ORDER BY priority DESC, timeStamp DESC")
    fun getNotesByPriorityDesc() : List<Note>
}
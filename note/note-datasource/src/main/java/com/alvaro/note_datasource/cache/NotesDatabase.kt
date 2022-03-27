package com.alvaro.note_datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase


@Database( entities = [NoteEntity::class] , version = NotesDatabase.DATABASE_VERSION)
abstract class NotesDatabase : RoomDatabase() {

    companion object{
        const val DATABASE_NAME = "noteDb"
        const val DATABASE_VERSION = 1
    }

    abstract fun getDao(): NotesDao

}
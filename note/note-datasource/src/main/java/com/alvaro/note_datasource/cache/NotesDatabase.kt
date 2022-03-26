package com.alvaro.note_datasource.cache

import androidx.room.Database

const val DATABASE_NAME = "noteDb"
const val DATABASE_VERSION = 1

@Database( entities = [NoteEntity::class] , version = DATABASE_VERSION)
abstract class NotesDatabase {

    /*RoomDatabase()

    abstract fun getDao(): NotesDao

    companion object{

        @Volatile
        private var dbInstance : NotesDatabase? = null
        private val LOCK = Any()

        operator fun invoke( context: Context) = dbInstance ?: synchronized(LOCK){
            dbInstance ?: buildDatabase(context).also{ dbInstance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder( context.applicationContext,
            NotesDatabase::class.java, DATABASE_NAME)
                .build()
    }*/
}
package com.example.notes.di

import android.content.Context
import androidx.room.Room
import com.alvaro.note_datasource.NoteRepositoryImpl
import com.alvaro.note_datasource.cache.NotesDao
import com.alvaro.note_datasource.cache.NotesDatabase
import com.alvaro.note_domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NotesDatabase::class.java, NotesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NotesDatabase): NotesDao {
        return noteDatabase.getDao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDatabase: NotesDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDatabase)
    }

}
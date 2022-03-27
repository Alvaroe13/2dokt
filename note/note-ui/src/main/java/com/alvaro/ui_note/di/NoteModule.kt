package com.alvaro.ui_note.di

import com.alvaro.note_domain.interactors.InsertNote
import com.alvaro.note_domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideInsertNoteInteractor(noteRepository: NoteRepository) : InsertNote {
        return InsertNote(noteRepository)
    }

}
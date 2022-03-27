package com.alvaro.ui_note.di

import com.alvaro.core.util.Logger
import com.alvaro.note_domain.interactors.InsertNote
import com.alvaro.note_domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteDetailModule {

    @Provides
    @Singleton
    @Named("NoteDetailView")
    fun provideLogger(): Logger {
        return Logger(
            tag = "NoteDetailView",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideInsertNoteInteractor(noteRepository: NoteRepository) : InsertNote {
        return InsertNote(noteRepository)
    }

}
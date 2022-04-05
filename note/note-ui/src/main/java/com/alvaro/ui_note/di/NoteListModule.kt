package com.alvaro.ui_note.di

import com.alvaro.core.util.Logger
import com.alvaro.note_domain.interactors.GetNotes
import com.alvaro.note_domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteListModule {

    @Provides
    @Singleton
    @Named("NoteListView")
    fun provideLogger(): Logger {
        return Logger(
            tag = "NoteListView",
            isDebug = true
        )
    }

    @Provides
    @Singleton
    fun provideGetNotesInteractor(noteRepository: NoteRepository) : GetNotes {
        return GetNotes(noteRepository)
    }
}
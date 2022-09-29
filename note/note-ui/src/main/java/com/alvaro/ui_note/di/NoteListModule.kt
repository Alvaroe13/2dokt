package com.alvaro.ui_note.di

import com.alvaro.core.util.Logger
import com.alvaro.note_domain.repository.NoteRepository
import com.alvaro.note_interactors.notelist.DeleteNote
import com.alvaro.note_interactors.notelist.GetNotes
import com.alvaro.note_interactors.notelist.RestoreNotesUseCase
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

    @Provides
    @Singleton
    fun provideDeleteNoteInteractor(noteRepository: NoteRepository): DeleteNote {
        return DeleteNote(noteRepository)
    }

    @Provides
    @Singleton
    fun provideRestoreNotesInteractor(noteRepository: NoteRepository): RestoreNotesUseCase {
        return RestoreNotesUseCase(noteRepository)
    }
}
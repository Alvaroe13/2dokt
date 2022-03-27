package com.alvaro.ui_note.notedetail

import com.alvaro.core.domain.LoadingState
import com.alvaro.note_domain.model.Note

data class NoteDetailState(
    val note: Note? = null,
    val loadingState: LoadingState = LoadingState.Idle
)

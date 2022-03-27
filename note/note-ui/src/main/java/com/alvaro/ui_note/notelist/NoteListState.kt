package com.alvaro.ui_note.notelist

import com.alvaro.core.domain.LoadingState
import com.alvaro.note_domain.model.Note

data class NoteListState(
    val noteList: List<Note>? = null,
    val loadingState: LoadingState = LoadingState.Idle
) {

    companion object {
        fun build(): NoteListState {
            return NoteListState()
        }
    }
}
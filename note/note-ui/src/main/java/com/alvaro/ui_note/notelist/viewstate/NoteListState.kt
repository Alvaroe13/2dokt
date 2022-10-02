package com.alvaro.ui_note.notelist.viewstate

import com.alvaro.core.domain.LoadingState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_domain.model.Note

data class NoteListState(
    val noteList: List<Note> = emptyList(),
    val loadingState: LoadingState = LoadingState.Idle,
    val uiComponent: UIComponent = UIComponent.Empty,
    val deletionState: DeletionState = DeletionState.Idle
) {

    companion object {
        fun build(): NoteListState {
            return NoteListState()
        }
    }
}
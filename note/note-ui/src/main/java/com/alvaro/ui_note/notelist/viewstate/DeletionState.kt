package com.alvaro.ui_note.notelist.viewstate

sealed interface DeletionState {
    object Idle: DeletionState
    object OnDeletion: DeletionState
}
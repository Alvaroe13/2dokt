package com.alvaro.ui_notedetail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alvaro.note_datasource.Repository

class ViewModelFactory(
    private val repository: Repository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(repository) as T
    }

}
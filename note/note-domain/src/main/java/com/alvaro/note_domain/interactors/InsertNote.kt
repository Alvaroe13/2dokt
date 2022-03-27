package com.alvaro.note_domain.interactors

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertNote( private val noteRepository: NoteRepository) {

    fun execute(note: Note) : Flow<DataState<Long>> = flow {
        try{
            emit( DataState.Data( data = noteRepository.insertNote(note)) )
        }catch (e: Exception){
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Dialog(
                        title = "Database Error",
                        message = e.message ?: "Unknown error"
                    )
                )
            )
        }
    }
}
package com.alvaro.note_domain.interactors.notedetailview

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateNote(private val noteRepository: NoteRepository) {


    fun execute(note: Note, scope: CoroutineScope): Flow<DataState<Int>> = flow{

        try {
            val row = scope.async { noteRepository.updateNote(note) }.await()
            emit( DataState.Data( data = row ))
        }catch (e :Exception){
            emit(
                DataState.Response(
                    uiComponent = UIComponent.Toast(
                        message = e.localizedMessage ?: "Error updating note"
                    )
                )
            )
        }
    }

}
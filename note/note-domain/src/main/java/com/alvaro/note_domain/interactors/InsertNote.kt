package com.alvaro.note_domain.interactors

import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository

class InsertNote(
    private val noteRepository: NoteRepository
) {

    suspend fun execute(note: Note){
        try{
            noteRepository.insertNote(note)
        }catch (e: Exception){
            println("InsertNote error = ${e.localizedMessage}")
        }
    }
}
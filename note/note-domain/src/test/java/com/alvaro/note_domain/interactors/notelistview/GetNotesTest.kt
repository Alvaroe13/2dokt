package com.alvaro.note_domain.interactors.notelistview

import com.alvaro.core.domain.DataState
import com.alvaro.note_domain.data.NoteDatabaseFake
import com.alvaro.note_domain.data.NoteFactory
import com.alvaro.note_domain.data.NoteRepositoryTestImpl
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetNotesTest {

    private lateinit var getNotes: GetNotes

    @Test
    fun `getAllNotes emptyDatabase Success`() = runBlocking {

        val noteDatabaseFake = NoteDatabaseFake(NoteFactory())
        val noteRepository: NoteRepository = NoteRepositoryTestImpl(noteDatabaseFake)
        getNotes = GetNotes(noteRepository)

        assert(noteDatabaseFake.notesDatabase.isEmpty())

        val emissions = getNotes.execute().toList()
        assert(emissions.size == 1)
        assert(emissions[0] is DataState.Data<List<Note>>)
        val notes = (emissions[0] as DataState.Data<List<Note>>).data
        assert(notes!!.isEmpty())
    }

    @Test
    fun `get All Notes populated database Success`() = runBlocking {

        val noteDatabaseFake = NoteDatabaseFake(NoteFactory())
        val noteRepository: NoteRepository = NoteRepositoryTestImpl(noteDatabaseFake)
        getNotes = GetNotes(noteRepository)

        assert(noteDatabaseFake.notesDatabase.isEmpty())
        noteDatabaseFake.addNotes()
        assert(noteDatabaseFake.notesDatabase.isNotEmpty())

        val emissions = getNotes.execute().toList()
        assert(emissions.size == 1)
        assert(emissions[0] is DataState.Data<List<Note>>)

        val notes = (emissions[0] as DataState.Data<List<Note>>).data
        assert(notes!!.isNotEmpty())
        assert(notes.size == noteDatabaseFake.notesDatabase.size)
        notes.forEachIndexed { index, note ->
            assert(note.id == noteDatabaseFake.notesDatabase[index].id)
        }
    }

}
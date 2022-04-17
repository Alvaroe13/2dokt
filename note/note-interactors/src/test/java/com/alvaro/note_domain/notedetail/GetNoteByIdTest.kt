package com.alvaro.note_domain.notedetail

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_datasource_test.data.NoteDatabaseFake
import com.alvaro.note_datasource_test.data.NoteFactory
import com.alvaro.note_datasource_test.data.NoteRepositoryTestImpl
import com.alvaro.note_domain.repository.NoteRepository
import com.alvaro.note_interactors.notedetail.GetNoteById
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteByIdTest {

    private lateinit var getNoteById: GetNoteById


    private lateinit var noteFactory: NoteFactory
    private lateinit var noteDatabaseFake: NoteDatabaseFake
    private lateinit var noteRepository: NoteRepository

    @Before
    fun setup() {
        noteFactory = NoteFactory()
        noteDatabaseFake = NoteDatabaseFake(noteFactory)
        noteRepository = NoteRepositoryTestImpl(noteDatabaseFake)

        getNoteById = GetNoteById(noteRepository)
    }


    @Test
    fun `get note by id success`() = runBlocking {

        // setup
        assert(noteDatabaseFake.notesDatabase.isEmpty())
        noteDatabaseFake.addNotes()
        assert(noteDatabaseFake.notesDatabase.isNotEmpty())

        //execution
        val note = noteDatabaseFake.notesDatabase[0]
        val emissions = getNoteById.execute(note.id!!).toList()

        //assertion
        assert(emissions[0] is DataState.Data)
        val noteFound = (emissions[0] as DataState.Data).data
        assert(note.id == noteFound!!.id)

    }

    @Test
    fun `get note by id Handle Error`() = runBlocking {

        // setup
        assert(noteDatabaseFake.notesDatabase.isEmpty())
        noteDatabaseFake.addNotes()
        assert(noteDatabaseFake.notesDatabase.isNotEmpty())

        //execution
        val note = noteDatabaseFake.notesDatabase[0]
        val emissions = getNoteById.execute(note.id!!, forceExceptionForTesting = true).toList()

        //assertion
        assert(emissions[0] is DataState.Response)
        val uiComponent = (emissions[0] as DataState.Response).uiComponent
        assert( (uiComponent as UIComponent.Toast).message == GetNoteById.ERROR_MSG)

    }
}
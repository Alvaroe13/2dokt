package com.alvaro.note_domain.notelist

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_datasource_test.data.NoteDatabaseFake
import com.alvaro.note_datasource_test.data.NoteFactory
import com.alvaro.note_datasource_test.data.NoteRepositoryTestImpl
import com.alvaro.note_domain.repository.NoteRepository
import com.alvaro.note_interactors.notelist.DeleteNote
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteNoteTest {

    private lateinit var deleteNote: DeleteNote


    private lateinit var noteFactory: NoteFactory
    private lateinit var noteDatabaseFake: NoteDatabaseFake
    private lateinit var noteRepository: NoteRepository


    @Before
    fun setup() {
        noteFactory = NoteFactory()
        noteDatabaseFake = NoteDatabaseFake(NoteFactory())
        noteRepository = NoteRepositoryTestImpl(noteDatabaseFake)

        deleteNote = DeleteNote(noteRepository)
    }

    @Test
    fun `delete note success`() = runBlocking {

        // setup
        assert(noteDatabaseFake.notesDatabase.isEmpty())
        noteDatabaseFake.addNotes()
        assert(noteDatabaseFake.notesDatabase.isNotEmpty())

        //execution
        val note = noteDatabaseFake.notesDatabase[0]
        val emissions = deleteNote.execute(note).toList()

        //assertion
        assert(emissions[0] is DataState.Data)
        assert(!noteDatabaseFake.notesDatabase.contains(note))

    }

    @Test
    fun `delete note failed`() = runBlocking {

        // setup
        assert(noteDatabaseFake.notesDatabase.isEmpty())
        noteDatabaseFake.addNotes()
        assert(noteDatabaseFake.notesDatabase.isNotEmpty())

        //execution
        val note = noteFactory.buildNote()
        val emissions = deleteNote.execute(note, forceExceptionForTesting = true).toList()

        //assertion
        assert(emissions[0] is DataState.Response)
        val uiComponent: UIComponent = (emissions[0] as DataState.Response).uiComponent
        assert(uiComponent is UIComponent.Toast)
        assert( (uiComponent as UIComponent.Toast).message == DeleteNote.ERROR_DELETING_NOTE)
    }


}
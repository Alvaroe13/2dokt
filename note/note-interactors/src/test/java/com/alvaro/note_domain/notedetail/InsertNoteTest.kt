package com.alvaro.note_domain.notedetail

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.core.util.TimeStampGenerator
import com.alvaro.note_datasource_test.data.NoteDatabaseFake
import com.alvaro.note_datasource_test.data.NoteFactory
import com.alvaro.note_datasource_test.data.NoteRepositoryTestImpl
import com.alvaro.note_domain.repository.NoteRepository
import com.alvaro.note_interactors.notedetail.InsertNote
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class InsertNoteTest {

    private lateinit var insertNote: InsertNote


    private lateinit var noteFactory: NoteFactory
    private lateinit var noteDatabaseFake: NoteDatabaseFake
    private lateinit var noteRepository: NoteRepository
    private lateinit var timeStampGenerator: TimeStampGenerator

    @Before
    fun setup() {
        noteFactory = NoteFactory()
        noteDatabaseFake = NoteDatabaseFake(NoteFactory())
        noteRepository = NoteRepositoryTestImpl(noteDatabaseFake)
        timeStampGenerator = TimeStampGenerator()

        insertNote = InsertNote(noteRepository, timeStampGenerator)
    }

    @Test
    fun `insert note success`() = runBlocking {

        // setup
        assert(noteDatabaseFake.notesDatabase.isEmpty())

        //execution
        val note = noteFactory.buildNote()
        val emissions = insertNote.execute(note).toList()

        //assertion
        assert(emissions[0] is DataState.Data)
        assert(noteDatabaseFake.notesDatabase.isNotEmpty())

    }


    @Test
    fun `insert note failed fue to empty title`() = runBlocking {

        // setup
        assert(noteDatabaseFake.notesDatabase.isEmpty())

        //execution
        val note = noteFactory.buildNoteWithEmptyTitle()
        val emissions = insertNote.execute(note).toList()

        //assertion
        assert(emissions[0] is DataState.Response)
        val uiComponent: UIComponent = (emissions[0] as DataState.Response).uiComponent
        assert(uiComponent is UIComponent.Toast)
        assert((uiComponent as UIComponent.Toast).message == InsertNote.ERROR_MSG_NO_VALID)

    }

    @Test
    fun `insert note failed fue to empty content`() = runBlocking {

        // setup
        assert(noteDatabaseFake.notesDatabase.isEmpty())

        //execution
        val note = noteFactory.buildNoteWithEmptyContent()
        val emissions = insertNote.execute(note).toList()

        //assertion
        assert(emissions[0] is DataState.Response)
        val uiComponent: UIComponent = (emissions[0] as DataState.Response).uiComponent
        assert(uiComponent is UIComponent.Toast)
        assert((uiComponent as UIComponent.Toast).message == InsertNote.ERROR_MSG_NO_VALID)

    }


    @Test
    fun `insert note handle Exception`() = runBlocking {

        // setup
        assert(noteDatabaseFake.notesDatabase.isEmpty())

        //execution
        val note = noteFactory.buildNote()
        val emissions = insertNote.execute(note, forceExceptionForTesting = true).toList()

        //assertion
        assert(emissions[0] is DataState.Response)
        val uiComponent: UIComponent = (emissions[0] as DataState.Response).uiComponent
        assert(uiComponent is UIComponent.Dialog)
        assert((uiComponent as UIComponent.Dialog).message == InsertNote.ERROR_MSG_UNKNOWN)

    }

}
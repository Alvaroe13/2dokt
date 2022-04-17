package com.alvaro.note_domain.notedetail

import com.alvaro.core.domain.DataState
import com.alvaro.core.domain.UIComponent
import com.alvaro.note_datasource_test.data.NoteDatabaseFake
import com.alvaro.note_datasource_test.data.NoteFactory
import com.alvaro.note_datasource_test.data.NoteRepositoryTestImpl
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import com.alvaro.note_interactors.notedetail.UpdateNote
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UpdateNoteTest {

    private lateinit var updateNote: UpdateNote


    private lateinit var noteFactory: NoteFactory
    private lateinit var noteDatabaseFake: NoteDatabaseFake
    private lateinit var noteRepository: NoteRepository

    @Before
    fun setup() {
        noteFactory = NoteFactory()
        noteDatabaseFake = NoteDatabaseFake(noteFactory)
        noteRepository = NoteRepositoryTestImpl(noteDatabaseFake)

        updateNote = UpdateNote(noteRepository)
    }

    @Test
    fun `update note success`() = runBlocking {

        // setup
        assert(noteDatabaseFake.notesDatabase.isEmpty())
        noteDatabaseFake.addNotes()
        assert(noteDatabaseFake.notesDatabase.isNotEmpty())
        val note = noteDatabaseFake.notesDatabase[0]
        note.title = "this is an updated title"
        note.content = "this is an updated content"
        note.priority = 5

        //execution
        val emissions = updateNote.execute(note).toList()

        //assertion
        assert(emissions[0] is DataState.Data)
        assert((emissions[0] as DataState.Data).data is Int)
        val updatedNote: Note = noteDatabaseFake.notesDatabase.find { it.id == note.id }
            ?: throw NullPointerException("Note not found in test")

        assert(updatedNote.id == note.id)
        assert(updatedNote.title == note.title)
        assert(updatedNote.content == note.content)
        assert(updatedNote.priority == note.priority)
        assert(updatedNote.timeStamp == note.timeStamp)
    }

    @Test
    fun `update note handle Exception`() = runBlocking {

        // setup
        assert(noteDatabaseFake.notesDatabase.isEmpty())
        noteDatabaseFake.addNotes()
        assert(noteDatabaseFake.notesDatabase.isNotEmpty())
        val note = noteDatabaseFake.notesDatabase[0]
        note.title = "this is an updated title"
        note.content = "this is an updated content"
        note.priority = 5

        //execution
        val emissions = updateNote.execute(note, forceExceptionForTesting = true).toList()

        //assertion
        assert(emissions[0] is DataState.Response)
        val uiComp = (emissions[0] as DataState.Response).uiComponent
        assert(uiComp is UIComponent.Toast)
        assert((uiComp as UIComponent.Toast).message == UpdateNote.ERROR_MSG)
    }
}
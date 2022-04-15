package com.alvaro.ui_note.notelistview

import app.cash.turbine.test
import com.alvaro.core.domain.LoadingState
import com.alvaro.core.util.Logger
import com.alvaro.note_datasource_test.data.NoteDatabaseFake
import com.alvaro.note_datasource_test.data.NoteFactory
import com.alvaro.note_datasource_test.data.NoteRepositoryTestImpl
import com.alvaro.note_domain.repository.NoteRepository
import com.alvaro.note_interactors.notelist.DeleteNote
import com.alvaro.note_interactors.notelist.GetNotes
import com.alvaro.ui_note.notelist.NoteListEvents
import com.alvaro.ui_note.notelist.NoteListViewModel
import com.alvaro.ui_note.util.DispatcherProviderTestImpl
import com.alvaro.ui_note.util.MainCoroutineRule
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class NoteListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: NoteListViewModel

    private lateinit var getNotes: GetNotes
    private lateinit var deleteNote: DeleteNote
    private lateinit var dispatcherProvider: DispatcherProviderTestImpl
    private lateinit var logger: Logger
    private lateinit var noteFactory: NoteFactory
    private lateinit var database: NoteDatabaseFake
    private lateinit var noteRepository: NoteRepository

    /*@Test
    fun `get notes success`() = runBlockingTest {

        assert(database.notesDatabase.isEmpty())

        database.addNotes()

        assert(database.notesDatabase.isNotEmpty())

        val states = mutableListOf<NoteListState>()

        val job = launch {
            viewModel.state.toList(states)
        }
        println("NoteListViewModelTest ${states} and size ${states.size}")
        viewModel.triggerEvent(NoteListEvents.GetNotes)

        assert(states.isNotEmpty())
        assert(states[0].loadingState is LoadingState.Loading)

        assert(states.size == 1)
        /*assert(states[1].noteList != null)
        states[1].noteList!!.forEachIndexed { index, note ->
            assert(database.notesDatabase[index].id == note.id)
        }*/
        //assert(states[0].loadingState.equals(false))

        job.cancel()
    }*/

    /*@Test
    fun `get notes success`() = runBlockingTest {

        val notes = noteFactory.buildNotes()
        val flow: Flow<DataState<List<Note>>> = flow {
            emit(DataState.Data(data = notes))
        }

        `when`(getNotes.execute()).thenReturn(flow)

        val states = mutableListOf<NoteListState>()

        val job = launch {
            viewModel.state.toList(states)
        }

        viewModel.triggerEvent(NoteListEvents.GetNotes)

        verify(states.isNotEmpty())
        verify(states[0].loadingState is LoadingState.Loading)
        verify(states[1].noteList!!.isNotEmpty())
        states[1].noteList!!.forEachIndexed { index, note ->
            verify(notes[index].id == note.id)
        }

        job.cancel()
    }*/

    @Test
    fun `collect with eager strategy`() = runBlockingTest {

        noteFactory = NoteFactory()
        database = NoteDatabaseFake(noteFactory)
        noteRepository = NoteRepositoryTestImpl(database)
        getNotes = GetNotes(noteRepository)
        deleteNote = DeleteNote(noteRepository)
        logger = Logger("NoteListViewModelTest", true)
        dispatcherProvider = DispatcherProviderTestImpl()

        viewModel = NoteListViewModel(getNotes, deleteNote, dispatcherProvider, logger)

        viewModel.state.test {
            /*assertEquals("one", awaitItem())
            assertEquals("two", awaitItem())*/
            viewModel.triggerEvent(NoteListEvents.GetNotes)
            assertTrue(awaitItem().loadingState is LoadingState.Idle)
            assertTrue(awaitItem().loadingState is LoadingState.Loading)
            assertTrue(awaitItem().loadingState is LoadingState.Idle)
            //assertTrue(awaitItem().noteList != null )
            awaitComplete()
        }


    }


}

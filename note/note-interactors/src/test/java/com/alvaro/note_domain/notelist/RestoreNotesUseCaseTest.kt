package com.alvaro.note_domain.notelist

import com.alvaro.core.domain.DataState
import com.alvaro.note_datasource_test.data.NoteDatabaseFake
import com.alvaro.note_datasource_test.data.NoteFactory
import com.alvaro.note_datasource_test.data.NoteRepositoryTestImpl
import com.alvaro.note_domain.model.Note
import com.alvaro.note_domain.repository.NoteRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class RestoreNotesUseCaseTest {
//
//    private lateinit var restoreNotesUseCase: RestoreNotesUseCase
//
//    private lateinit var noteFactory: NoteFactory
//    private lateinit var noteDatabaseFake: NoteDatabaseFake
//    private lateinit var noteRepository: NoteRepository
//
//
//    @Before
//    fun setup() {
//        noteFactory = NoteFactory()
//        noteDatabaseFake = NoteDatabaseFake(noteFactory)
//        noteRepository = NoteRepositoryTestImpl(noteDatabaseFake)
//
//        restoreNotesUseCase = RestoreNotesUseCase(noteRepository)
//
//        noteDatabaseFake.addNotes()
//    }
//
//    @After
//    fun tearDown(){
//        noteDatabaseFake.notesDatabase.clear()
//    }
//
//    @Test
//    fun `restore cached notes success`() = runBlocking {
//
//        assert(noteDatabaseFake.notesDatabase.isNotEmpty())
//
//        //Retrieve notes to then delete
//        val notesBeforeDeletion = noteRepository.getAllNotes()
//
//        //deletion
//        noteDatabaseFake.notesDatabase.removeFirst()
//        noteDatabaseFake.notesDatabase.removeFirst()
//
//       /* val notesAfterDeletion = noteRepository.getAllNotes()
//        val notesCached = noteRepository.getCacheNotes()*/
//
//        val emissions = restoreNotesUseCase().toList()
//
//        //assertion
//        assert(emissions[0] is DataState.Data)
//        val notesFetched = (emissions[0] as DataState.Data<List<Note>>).data
//        println("RestoreNotesUseCaseTest notesFetched?.size ${notesFetched?.size}, notes.size ${notesBeforeDeletion.size}")
//        assert(notesBeforeDeletion.size == notesFetched?.size!!)
//
//        notesBeforeDeletion.forEach { _note ->
//            assert(notesBeforeDeletion.contains(_note))
//        }
//    }

}
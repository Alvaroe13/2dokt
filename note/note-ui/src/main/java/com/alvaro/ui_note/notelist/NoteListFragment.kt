package com.alvaro.ui_note.notelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alvaro.ui_note.R
import com.alvaro.ui_note.databinding.FragmentNoteListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    companion object{
        private const val TAG = "NoteListFragment"
    }


    //, NoteListAdapter.ClickHandler

   /* private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private var itemHelper: Any? = null
    private lateinit var noteList: List<Note>
    private lateinit var layout : View*/

    private lateinit var binding: FragmentNoteListBinding

    private val viewModel : NoteListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNoteListBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        /*layout = view
        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()
        getAllNotes()
        deleteNote()*/

        fabClicked()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                println("${TAG} triggered ${state}")
            }
        }

    }

    private fun fabClicked() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_NoteListFragment_to_noteDetailFragment)

        }
    }

   /* private fun initRecyclerView() {
        noteAdapter = NoteAdapter(this)
        recycler.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }



    private fun getAllNotes() {
        viewModel.getAllNotes().observe(viewLifecycleOwner, Observer { response ->
            noteList = response
            println("MainFragment, response size -> = ${response.size}")
            noteAdapter.differAsync.submitList(response)
        })

    }

    private fun deleteNote() {

        itemHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = noteAdapter.differAsync.currentList[position]
                viewModel.deleteNote(note)
                noteAdapter.differAsync.submitList(noteList)
                println("MainFragment, onSwiped called!")
            }
        }

        val swipeToDelete = ItemTouchHelper(itemHelper as ItemTouchHelper.SimpleCallback)
        swipeToDelete.attachToRecyclerView(recycler)
    }

    override fun itemClick(position: Int) {
        val note = noteList[position]
        Log.d(TAG, "itemClick: priority = ${note.priority} ")
        val bundle = bundleOf(
            "id" to note.id,
            "title" to note.title,
            "content" to note.content,
            "priority" to note.priority
        )
        NavHelper.navigateWithStack(layout ,R.id.action_mainFragment_to_noteRoomFragment, bundle)
    }*/

}



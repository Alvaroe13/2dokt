package com.alvaro.ui_notelist

import androidx.fragment.app.Fragment

private const val TAG = "MainFragment"

class MainFragment : Fragment(R.layout.fragment_note_list), NoteListAdapter.ClickHandler {

   /* private lateinit var viewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private var itemHelper: Any? = null
    private lateinit var noteList: List<Note>
    private lateinit var layout : View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout = view
        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()
        getAllNotes()
        fabClicked()
        deleteNote()
    }

    private fun initRecyclerView() {
        noteAdapter = NoteAdapter(this)
        recycler.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun fabClicked() {
        fab.setOnClickListener {
            NavHelper.navigateWithStack(layout, R.id.action_mainFragment_to_noteRoomFragment, null)
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



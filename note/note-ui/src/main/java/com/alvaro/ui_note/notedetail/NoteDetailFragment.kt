package com.alvaro.ui_note.notedetail

import androidx.fragment.app.Fragment
import com.alvaro.ui_note.R

private const val TAG = "NoteRoomFragment"

class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {

   //ui
   /* private var toolbar: androidx.appcompat.widget.Toolbar? = null
    private var toolbarTitle: TextView? = null
    private var spinner: Spinner? = null
    private lateinit var viewModel: NoteViewModel
    private lateinit var layout :View
    //vars
    private lateinit var title: String
    private lateinit var content: String
    private var priority: Int = 1
    //bundle var
    private var incomingTitle: String? = null
    private var incomingContent: String? = null
    private var incomingPriority: Int = 0
    private var isUpdating: Boolean = false
    private var incomingID = 0
    private var timeStamp = DateGenerator.getDate()

    val SPINNER_DEFAULT_VALUE


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: called")
        layout = view
        viewModel = (activity as MainActivity).viewModel
        setHasOptionsMenu(true)
        toolbar()
        spinnerListener()
        incomingBundle()
        setSpinnerPosition()
        noteTitle.setText(incomingTitle)
        noteContent.setText(incomingContent)
    }


    private fun incomingBundle() {
        if (arguments != null) {
            Log.d(TAG, "incomingBundle: bundle not null")
            //needed to keep track if toolbar icon should act as insert or update in DAO
            isUpdating = true
            incomingID = arguments?.getInt("id")!!
            incomingTitle = arguments?.getString("title").toString()
            incomingContent = arguments?.getString("content").toString()
            incomingPriority = requireArguments().getInt("priority")
        } else {
            Log.d(TAG, "incomingBundle: bundle NULL")
            //needed to keep track if toolbar icon should act as insert or update in DAO
            isUpdating = false
        }
    }

    private fun toolbar() {
        toolbar = activity?.toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarTitle = activity?.tbTitle
        toolbarTitle?.text = "Note"

        spinner = activity?.spinner
        spinnerVisible()
    }

    private fun spinnerListener() {
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long){
                //this value is set only if user touches spinner.
                priority = position + 1
            }
        }

    }

    private fun updateNote() {
        title = noteTitle.text?.trim().toString()
        content = noteContent.text?.trim().toString()
        val updateNote = Note(incomingID, title, content, priority, timeStamp)
        viewModel.updateNote(updateNote)
        goToMain()
        hideKeyboard()
        Toast.makeText(context, "Note updated", Toast.LENGTH_SHORT).show()
    }

    private fun saveNote() {
        title = noteTitle.text?.trim().toString()
        content = noteContent.text?.trim().toString()
        val note = Note(0, title, content, priority, timeStamp)
        if (title.isNotEmpty() && content.isNotEmpty()) {
            viewModel.insertNote(note)
            goToMain()
            hideKeyboard()
            Toast.makeText(activity, "Note saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "No field can be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setSpinnerPosition() {
        Log.d(TAG, "setSpinnerPosition: incomingPriority = $incomingPriority")
        if (incomingPriority == 0){
            spinner?.setSelection(SPINNER_DEFAULT_VALUE)
        }else{
            spinner?.setSelection(incomingPriority - 1)
        }
    }

    private fun goToMain(){
        NavHelper.navigateWithoutStack(layout, R.id.action_noteRoomFragment_to_mainFragment2, null )
    }

    private fun hideKeyboard(){
        try {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    //-------------------------------- override functions -----------------------------------------//

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.saveNote) {
            if (isUpdating) {
                Log.d(TAG, "onOptionsItemSelected: isUpdating == true")
                updateNote()
            } else {
                Log.d(TAG, "onOptionsItemSelected: isUpdating == false ")
                saveNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun spinnerVisible(){
        spinner?.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: called")
        spinnerVisible()
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: called")
        toolbarTitle!!.text = getString(R.string.app_name)
        spinner?.visibility = View.GONE
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }*/

}
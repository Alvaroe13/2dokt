package com.alvaro.ui_note.notedetail

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.alvaro.core.domain.UIComponent
import com.alvaro.core.util.TimeStampGenerator
import com.alvaro.note_domain.model.Note
import com.alvaro.ui_note.R
import com.alvaro.ui_note.databinding.FragmentNoteDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {

    companion object {
        private const val TAG = "NoteDetailFragment"
    }

    lateinit var binding: FragmentNoteDetailBinding
    private val viewModel: NoteDetailViewModel by viewModels()
    private var note: Note? = null
    private var priority: Int = 1

    @Inject
    lateinit var timeStampGenerator: TimeStampGenerator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNoteDetailBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        spinnerListener()
        subscribeObservers()
        clickListeners()
    }

    private fun clickListeners() {
        binding.saveIcon.setOnClickListener {
            val note = Note.build(
                id = note?.id,
                title = binding.noteTitle.text?.trim().toString(),
                content = binding.noteContent.text?.trim().toString(),
                priority = priority,
                timeStamp = timeStampGenerator.getDate() ?: "-"
            )
            triggerEvent(note)
        }
    }

    private fun subscribeObservers() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.response.collect { value: UIComponent ->
                    when (value) {
                        is UIComponent.Dialog -> {
                            showDialog(value)
                        }
                        is UIComponent.Toast -> {
                            showToast(value)
                        }
                        else -> {}
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state: NoteDetailState ->
                    popBackStack(state)
                    showDetails(state)
                    println("${TAG} triggered ${state}")
                }
            }
        }
    }

    private fun popBackStack(state: NoteDetailState){
        if (state.saveNoteType is SaveNoteType.InsertNote ||
            state.saveNoteType is SaveNoteType.UpdateNote) {
            findNavController().popBackStack()
        }
    }

    private fun showDetails(state: NoteDetailState){
        state.note?.let {
            note = it
            displayDetails(it)
        }
    }

    private fun displayDetails(note: Note) {
        binding.apply {
            noteTitle.setText(note.title)
            noteContent.setText(note.content)
            spinnerPriority.setSelection(note.priority - 1)
        }
    }

    private fun triggerEvent(note: Note) {
        if (note.id == null) {
            viewModel.triggerEvent(NoteDetailsEvents.InsertNote(note))
        } else {
            viewModel.triggerEvent(NoteDetailsEvents.UpdateNote(note))
        }
    }

    private fun spinnerListener() {
        binding.spinnerPriority.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    priority = position + 1
                }
            }

    }

    private fun showToast(uiComponent: UIComponent.Toast){
        Toast.makeText(requireContext(), uiComponent.message, Toast.LENGTH_LONG).show()
    }

    private fun showDialog(uiComponent: UIComponent.Dialog){
        val builder = AlertDialog.Builder(this.context)
        builder.setTitle(uiComponent.title)
        builder.setMessage(uiComponent.message)
        builder.show()
    }

}
package com.alvaro.ui_note.notelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alvaro.note_domain.model.Note
import com.alvaro.ui_note.databinding.NoteSingleLayoutBinding

class NoteListAdapter(
    private val listener: ClickHandler
) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {


    private val differCallBack = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.content == newItem.content
                    && oldItem.title == newItem.title
                    && oldItem.timeStamp == newItem.timeStamp
                    && oldItem.priority == newItem.priority
        }
    }

    val differAsync = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val item = NoteSingleLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteViewHolder(item)
    }

    override fun getItemCount(): Int {
        return differAsync.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = differAsync.currentList[position]
        holder.bind(note)
    }

    inner class NoteViewHolder(private val binding: NoteSingleLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(note: Note) {
            binding.apply {
                tvTitle.text = note.title
                tvContent.text = note.content
                tvDate.text = note.timeStamp
                tvPriority.text = note.priority.toString()
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.itemClick(differAsync.currentList[position].id!!)
            }
        }

    }

    interface ClickHandler {
        fun itemClick(noteId: String)
    }

}
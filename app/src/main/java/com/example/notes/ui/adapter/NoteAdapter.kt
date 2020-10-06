package com.example.notes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.model.Note
import kotlinx.android.synthetic.main.note_single_layout.view.*

class NoteAdapter(
    private val listener : ClickHandler
) : RecyclerView.Adapter<NoteAdapter. NoteViewHolder>() {


    private val differCallBack = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.content == newItem.content  && oldItem.title == newItem.title
                    && oldItem.timeStamp == newItem.timeStamp
        }

    }

    val differAsync = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.note_single_layout, parent, false)
        return NoteViewHolder(item)
    }

    override fun getItemCount(): Int {
       return differAsync.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = differAsync.currentList[position]
        holder.itemView.apply {
            tvTitle.text = note.title
            tvContent.text = note.content
            tvDate.text = note.timeStamp
            tvPriority.text = note.priority.toString()


        }
    }

    inner class NoteViewHolder(view :View) : RecyclerView.ViewHolder(view), View.OnClickListener{

        init {
            view.setOnClickListener (this)
        }

        override fun onClick(v: View?) {
             val position = adapterPosition
            if(position != RecyclerView.NO_POSITION ){
                listener.itemClick(position)
            }
        }


    }

    interface ClickHandler{
        fun itemClick(position: Int)
    }


}
package com.example.todolist.ui.dashboard

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.Note
import com.example.todolist.databinding.ItemNoteBinding
import java.text.SimpleDateFormat


class NoteAdapter : PagingDataAdapter<Note, NoteAdapter.ViewHolder>(Companion) {


    inner class ViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind() {
            val note = getItem(absoluteAdapterPosition) ?: return

            itemView.apply {
                binding.tvTitle.text = note.title
                binding.tvNote.text = note.note
                binding.tvDate.text = note.date.toString()


                @RequiresApi(Build.VERSION_CODES.N)
                @SuppressLint("SimpleDateFormat")

                fun dateToCreateNote() {
                    val simpleDateFormat = SimpleDateFormat("dd MMM yyyy")
                    val dateString = simpleDateFormat.format(note.date)
                    binding.tvDate.text = dateString
                }
                dateToCreateNote()

                binding.apply {

                    setOnLongClickListener {
                        onNoteClickListener?.let {
                            it(note)
                        }
                        true

                    }

                    setOnClickListener {
                        onNoteClickListener2?.let {
                            it(note)
                        }
                    }

                }

            }

        }
    }

    private var onNoteClickListener: ((Note) -> Unit)? = null

    private var onNoteClickListener2: ((Note) -> Unit)? = null


    fun setOnNoteLongClickListener(listener: (Note) -> Unit) {
        onNoteClickListener = listener
    }

    fun setOnNoteClickListener(listener: (Note) -> Unit) {
        onNoteClickListener2 = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()

    }

    companion object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
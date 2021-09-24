package com.example.todolist.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.Note
import com.example.todolist.databinding.ItemNoteBinding
import com.google.firebase.auth.FirebaseAuth


class NoteAdapter : PagingDataAdapter<Note, NoteAdapter.ViewHolder>(Companion) {


    inner class ViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val note = getItem(absoluteAdapterPosition) ?: return

            itemView.apply {
                binding.tvTitle.text = note.title
                binding.tvNote.text = note.note
                binding.tvDate.text = note.date.toString()

                val uid = FirebaseAuth.getInstance().uid!!
                //  binding.btnDeleteOwnPost.isVisible = uid == post.authorUid
                binding.IbDone.setImageResource(
                    if (note.isLiked) {
                        R.drawable.ic_check
                    } else {
                        R.drawable.ic_check_mark
                    }
                )
                binding.apply {
                    IbDone.setOnClickListener {
                        onLikeClickListener?.let { click ->
                            if (!note.isLiking)
                                click(note, layoutPosition)


                        }
                    }
                    /* btnDeleteOwnPost.setOnClickListener {
                         onNoteClickListener?.let { click ->
                             click(note)

                         }
                     }*/

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

    private var onLikeClickListener: ((Note, Int) -> Unit)? = null


    private var onDeleteNoteClickListener: ((Note) -> Unit)? = null


    //private var onNoteClickListener: ((Note) -> Unit)? = null


    fun setOnNoteLongClickListener(listener: (Note) -> Unit) {
        onNoteClickListener = listener
    }

    fun setOnNoteClickListener(listener: (Note) -> Unit) {
        onNoteClickListener2 = listener
    }

    fun setOnLikeClickListener(listener: (Note, Int) -> Unit) {
        onLikeClickListener = listener
    }


    fun setOnDeleteNoteClickListener(listener: (Note) -> Unit) {
        onDeleteNoteClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()

    }

    private var onItemClickListener: ((Note) -> Unit)? = null

    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener

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
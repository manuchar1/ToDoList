package com.example.todolist.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.Note
import com.example.todolist.databinding.ItemNoteBinding
import com.google.firebase.auth.FirebaseAuth


class NoteAdapter () : RecyclerView.Adapter<NoteAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val note = differ.currentList[absoluteAdapterPosition]

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
                    /*   btnDeleteOwnPost.setOnClickListener {
                           onDeletePostClickListener?.let { click ->
                               click(note)

                           }
                       }*/

                    setOnClickListener {
                        onNoteClickListener?.let { it(note) }
                    }

                }

            }
        }
    }

    private var onNoteClickListener: ((Note) -> Unit)? = null

    private var onLikeClickListener: ((Note, Int) -> Unit)? = null


    private var onDeleteNoteClickListener: ((Note) -> Unit)? = null


    //  private var onNoteClickListener: ((Post) -> Unit)? = null


    fun setOnNoteClickListener(listener: (Note) -> Unit) {
        onNoteClickListener = listener
    }

    fun setOnLikeClickListener(listener: (Note, Int) -> Unit) {
        onLikeClickListener = listener
    }


    fun setOnDeleteNoteClickListener(listener: (Note) -> Unit) {
        onDeleteNoteClickListener = listener
    }



    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind()

    }

    private var onItemClickListener: ((Note) -> Unit)? = null

    fun setOnItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}
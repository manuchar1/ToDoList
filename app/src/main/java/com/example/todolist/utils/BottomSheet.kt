package com.example.todolist.utils

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.todolist.app.snackBar
import com.example.todolist.data.Note
import com.example.todolist.databinding.BottomSheetFragmentBinding
import com.example.todolist.ui.DeleteNoteDialog
import com.example.todolist.ui.dashboard.NoteAdapter
import com.example.todolist.ui.dashboard.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheet : BottomSheetDialogFragment() {

    private val viewModel: NotesViewModel by viewModels()
  //  private lateinit var noteApter: NoteAdapter

  //  lateinit var binding: BottomSheetFragmentBinding

    var binding: BottomSheetFragmentBinding? = null
  //  private var onNoteClickListener: ((Note) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetFragmentBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding?.btnMarkAsDone?.setOnClickListener {
            snackBar("ggsggsgs")
           // activity?.recreate()
        }


       /* binding?.btnDelete?.setOnClickListener {
            *//*noteApter.setOnDeleteNoteClickListener {
             viewModel.deleteNote(note)
         }*//*
            snackBar("Click")

        }*/



      //  noteApter = NoteAdapter()

        /*    binding.btnDelete.setOnClickListener {
                onNoteClickListener?.let { click ->
                    click(Note())

                }
            }*/


/*



        noteApter.setOnNoteClickListener { note ->
                binding.btnDelete.setOnClickListener {
                DeleteNoteDialog().apply {
                    setPositiveListener {
                        viewModel.deleteNote(note)
                    }
                }.show(childFragmentManager, null)
                dismiss()
                activity?.recreate()
            }

        }


        binding.btnMarkAsDone.setOnClickListener {
            dismiss()
            activity?.recreate()
        }
        *//*   binding.btnDelete.setOnClickListener {
                DeleteNoteDialog().apply {
                 setPositiveListener {
                     viewModel.deleteNote(note)
                 }
             }.show(childFragmentManager, null)
               dismiss()
               activity?.recreate()
           }*//*
        binding.btnShare.setOnClickListener {
            activity?.recreate()
        }*/
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
            .apply { (this as BottomSheetDialog).behavior.expandedOffset = 300 }
    }

}
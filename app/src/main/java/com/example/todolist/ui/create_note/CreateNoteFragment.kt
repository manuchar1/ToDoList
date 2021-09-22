package com.example.todolist.ui.create_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.todolist.app.snackBar
import com.example.todolist.databinding.CreateNoteFragmentBinding
import com.example.todolist.utils.EventObserver

class CreateNoteFragment : Fragment() {

    private val viewModel: CreateNoteViewModel by viewModels()
    private lateinit var binding: CreateNoteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateNoteFragmentBinding.inflate(inflater, container, false)
        viewModel.init(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etNote.doOnTextChanged { text, _, _, _ ->
            binding.btnDone.isGone = text!!.isEmpty()
        }
        binding.btnDone.setOnClickListener {
            viewModel.createNote(
                binding.etTittle.text.toString(),
                binding.etNote.text.toString()
            )
        }
        subscribeToObservers()

    }

    private fun subscribeToObservers() {

        viewModel.createNoteStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
              //  binding.btnPublish.hideProgress(R.string.publish)
                snackBar(it)
            },
            onLoading = { /*showProgressButton(binding.btnPublish)*/ }
        ) {
          //  binding.btnPublish.hideProgress(R.string.publish)
           // findNavController().popBackStack()

            snackBar("Success")

        })
    }
}
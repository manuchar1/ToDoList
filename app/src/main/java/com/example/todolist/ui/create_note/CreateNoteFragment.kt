package com.example.todolist.ui.create_note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.example.todolist.app.snackBar
import com.example.todolist.databinding.CreateNoteFragmentBinding
import com.example.todolist.utils.EventObserver

class CreateNoteFragment : Fragment(),View.OnClickListener {



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

        binding.btnBack.setOnClickListener(this)
        binding.btnUp.setOnClickListener(this)

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
        dropDownEditText()


    }

    private fun dropDownEditText() {
        binding.etTittle.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) {
                binding.btnBack.isVisible = false
                binding.btnUp.isGone = false
                binding.etTittle.marginLeft
                binding.etTittle.textSize = 40F
            } else {
                binding.etTittle.textSize = 20F
                binding.btnBack.isVisible = true
                binding.btnUp.isGone = true

            }
        }
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
            findNavController().navigate(R.id.action_createNoteFragment_to_notesFragment)

        })
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnBack -> {
                    findNavController().popBackStack()
                   // findNavController().navigate(R.id.action_createNoteFragment_to_dashboardFragment)
                    return
                }
                R.id.btnUp -> {
                    binding.etTittle.textSize = 20F
                    binding.btnBack.isVisible = true
                    binding.btnUp.isGone = true
                }
            }
        }
    }

}
package com.example.todolist.ui.details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.R
import com.example.todolist.app.snackBar
import com.example.todolist.databinding.FragmentDetailsBinding
import com.example.todolist.ui.auth.LoginFragmentDirections
import com.example.todolist.ui.create_note.CreateNoteViewModel
import com.example.todolist.utils.EventObserver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class DetailsFragment : Fragment() {


    private val detailArg by navArgs<DetailsFragmentArgs>()

    private lateinit var binding: FragmentDetailsBinding

    private val viewModel2: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance()




        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etNote.doOnTextChanged { text, _, _, _ ->

            binding.btnDone.isGone = text!!.isEmpty()
            val currentTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")
            val formattedCurrentTime = currentTime.format(formatter)
            binding.tvLastModified.text = "Last modified: $formattedCurrentTime"
        }
        binding.btnDone.setOnClickListener {

            viewModel2.updateNote(
                binding.etTittle.text.toString(),
                binding.etNote.text.toString()

            )

        }

        binding.btnLogout.setOnClickListener {

            user.signOut()

            val action = LoginFragmentDirections.actionGlobalLoginFragment()
            activity?.findNavController(R.id.fragmentContainerView)?.navigate(action)
        }

        dropDownEditText()
        subscribeToObservers()


        val details = detailArg.details

        val simpleDateFormat = java.text.SimpleDateFormat("MMM dd yyyy")
        val dateString = simpleDateFormat.format(details.date)

        binding.etTittle.setText(details.title)
        binding.etNote.setText(details.note)
        binding.tvCreated.text = "Created: $dateString"


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

        viewModel2.updateNoteStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                snackBar(it)
                Log.e("Tag", it)
                findNavController().popBackStack()
            },
        ) {
            snackBar("Success?????")
        })
    }
}
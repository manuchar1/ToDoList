package com.example.todolist.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.todolist.data.Note
import com.example.todolist.data.NoteUpdate
import com.example.todolist.databinding.FragmentDetailsBinding
import com.example.todolist.ui.auth.LoginFragmentDirections
import com.example.todolist.utils.EventObserver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class DetailsFragment : Fragment() {


    private val viewModel: DetailsViewModel by viewModels()
    private val detailArg by navArgs<DetailsFragmentArgs>()

    private lateinit var binding: FragmentDetailsBinding

    private val db = FirebaseFirestore.getInstance()
    private val uid: String = ""

    private val curId =  db.collection("notes").id




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.init(requireContext())
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance()
        val uid = FirebaseAuth.getInstance().uid
        val db = FirebaseFirestore.getInstance().collection("note")

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etNote.doOnTextChanged { text, _, _, _ ->
            binding.btnDone.isGone = text!!.isEmpty()
        }
        binding.btnDone.setOnClickListener {
            val title = binding.etTittle.text.toString()
            val note = binding.etNote.text.toString()
            val date = binding.tvDate.text.toString()
            val noteUpdate = NoteUpdate(curId,note,title)
            viewModel.updateNote(noteUpdate)
        }

        binding.btnLogout.setOnClickListener {

            user.signOut()

            val action = LoginFragmentDirections.actionGlobalLoginFragment()
            activity?.findNavController(R.id.fragmentContainerView)?.navigate(action)


            // findNavController().navigate(R.id.action_detailsFragment_to_loginFragment2)
        }

        dropDownEditText()
        subscribeToObservers()

        val details = detailArg.details
        binding.etTittle.setText(details.title)
        binding.etNote.setText(details.note)


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
        viewModel.updateNoteStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.progressBar3.isVisible = false
                snackBar(it)
                Log.e("Tag", it)
            },
            onLoading = {
                binding.progressBar3.isVisible = true
            }
        ) {
            binding.progressBar3.isVisible = false
          //  snackbar(requireContext().getString(R.string.profile_updated))
        })
    }
}
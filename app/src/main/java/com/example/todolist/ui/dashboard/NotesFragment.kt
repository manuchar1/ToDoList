package com.example.todolist.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.todolist.databinding.DashboardFragmentBinding

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.ui.create_note.CreateNoteViewModel
import kotlinx.coroutines.launch


class NotesFragment : Fragment() {



    private val viewModel: NotesViewModel by viewModels()
    private lateinit var binding: DashboardFragmentBinding
    private lateinit var notedApter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DashboardFragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  dropDownEditText()

        binding.btnAddNotes.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_createNoteFragment)
        }
        /*lifecycleScope.launch {
            viewModel.pagingFlow.collect {
                notedApter.submitData(it)
            }
        }*/

        setupRecyclerView()


    }




/*    fun dropDownEditText() {
        binding.tvTittle.onFocusChangeListener  = View.OnFocusChangeListener { view, b ->
            if (b){
                // do something when edit text get focus
                //  textView.text = "EditText now focused."
                binding.tvTittle.textSize = 40F
            }else{
                // do something when edit text lost focus
                *//*  textView.text = "EditText lost focus."
                  textView.append("\nSoft keyboard hide.")
                  textView.append("\n\nYou entered : ${editText.text}")*//*

                // hide soft keyboard when edit text lost focus
                // hideSoftKeyboard(editText)
               // binding.tvTittle.height = 10
            }
        }
    }*/



    private fun setupRecyclerView() = binding.recyclerview.apply {
        adapter = notedApter
        layoutManager = GridLayoutManager(requireContext(),2)
        itemAnimator = null
    }


}
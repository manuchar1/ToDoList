package com.example.todolist.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.databinding.DashboardFragmentBinding
import androidx.core.content.ContextCompat.getSystemService

import android.view.inputmethod.InputMethodManager

import android.graphics.Rect

import android.widget.EditText

import android.view.MotionEvent
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.todolist.R


class DashboardFragment : Fragment() {



    private lateinit var viewModel: DashboardViewModel
    private lateinit var binding: DashboardFragmentBinding

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


}
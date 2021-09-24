package com.example.todolist.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.todolist.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private val detailArg by navArgs<DetailsFragmentArgs>()

    private lateinit var binding:FragmentDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dropDownEditText()

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
}
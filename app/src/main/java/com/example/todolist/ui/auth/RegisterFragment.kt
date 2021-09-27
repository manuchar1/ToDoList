package com.example.todolist.ui.auth


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.RegisterFragmentBinding
import com.example.todolist.app.showProgressButton
import com.example.todolist.app.snackBar
import com.example.todolist.app.spannableSentence
import com.example.todolist.utils.EventObserver
import com.github.razir.progressbutton.hideProgress


class RegisterFragment : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var binding: RegisterFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegisterFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spannableSentence(
            18,
            24,
            R.string.if_already_a_member,
            R.id.action_registerFragment_to_loginFragment,
            binding.tvGoToLogin
        )

        binding.btnRegister.setOnClickListener {
            binding.apply {
                viewModel.register(
                    etSingUpEmail.text.toString(),
                    etName.text.toString(),
                    etSingUpPassword.text.toString(),
                    etSingUpRePassword.text.toString()

                )

            }

        }

        subscribeToObservers()
    }


    private fun subscribeToObservers() {
        viewModel.registerStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.btnRegister.hideProgress(R.string.register)
                snackBar(it)

            },
            onLoading = {
                showProgressButton(binding.btnRegister)
            }

        ) {
            binding.btnRegister.hideProgress(R.string.register)
            snackBar(getString(R.string.success_registration))
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)

        })
    }


}
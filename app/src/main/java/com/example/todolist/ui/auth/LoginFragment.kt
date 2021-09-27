package com.example.todolist.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.LoginFragmentBinding
import com.example.todolist.app.showProgressButton
import com.example.todolist.app.snackBar
import com.example.todolist.app.spannableSentence
import com.example.todolist.utils.EventObserver
import com.github.razir.progressbutton.hideProgress
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater, container, false)
        viewModel.init(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spannableSentence(
            10,
            18,
            R.string.if_not_registered,
            R.id.action_loginFragment_to_registerFragment,
            binding.tvGoAndRegister
        )

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )

        }

        val user = Firebase.auth.currentUser
        if (user == null) {

            subscribeToObservers()
        }


    }


    private fun subscribeToObservers() {
        viewModel.loginStatus.observe(viewLifecycleOwner, EventObserver(
            onError = {
                binding.btnLogin.hideProgress(R.string.login)
                snackBar(it)
            },
            onLoading = { showProgressButton(binding.btnLogin) },

            ) {
            binding.btnLogin.hideProgress(R.string.login)

            //   DataStore.saveToken("login")


            /*    sharedPreferences = UserPreference(requireContext())
                sharedPreferences.saveUserSession(true)
                sharedPreferences.saveToken("login")*/
            findNavController().navigate(R.id.action_loginFragment_to_navHostFragment)

        })

    }
}
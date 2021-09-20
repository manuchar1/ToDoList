package com.example.todolist.ui.splash

import android.os.Bundle
import android.os.Handler
import android.support.v4.media.session.MediaSessionCompat.KEY_TOKEN
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.SplashFragmentBinding
import com.example.todolist.utils.Constants
import com.example.todolist.utils.Constants.LOGIN_TOKEN
import com.example.todolist.utils.DataStore

class SplashFragment : Fragment() {


    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: SplashFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        skipFragments()
        Handler().postDelayed({
        }, 3000)
    }


    private fun skipFragments() {

        when (DataStore.token()) {

            "login" -> findNavController().navigate(R.id.action_splashFragment_to_dashboardFragment)
            else -> findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }

    }
}
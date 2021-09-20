package com.example.todolist.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.R
import com.example.todolist.repository.AuthRepository
import com.example.todolist.utils.Constants.MAX_USERNAME_LENGTH
import com.example.todolist.utils.Constants.MIN_PASSWORD_LENGTH
import com.example.todolist.utils.Constants.MIN_USERNAME_LENGTH
import com.example.todolist.utils.Event
import com.example.todolist.utils.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(


) : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    lateinit var repository: AuthRepository
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main

    fun init(c: Context) {
        context = c
    }

    private val _registerStatus = MutableLiveData<Event<Resource<AuthResult>>>()
    val registerStatus: LiveData<Event<Resource<AuthResult>>> = _registerStatus

    private val _loginStatus = MutableLiveData<Event<Resource<AuthResult>>>()
    val loginStatus: LiveData<Event<Resource<AuthResult>>> = _loginStatus



    fun login(email: String, password: String) {
        repository = AuthRepository()
        if (email.isEmpty() || password.isEmpty()) {
            val error = context.getString(R.string.error_input_empty)
            _loginStatus.postValue(Event(Resource.Error(error)))

        } else {
            _loginStatus.postValue(Event(Resource.Loading()))
            viewModelScope.launch(dispatcher) {
                val result = repository.login(email, password)
                _loginStatus.postValue(Event(result))
            }
        }
    }


    fun register(email: String, username: String, password: String, confirmPassword: String) {
        repository = AuthRepository()
        val error = when {
            email.isEmpty() || username.isEmpty() || password.isEmpty() -> context.getString(
                R.string.error_input_empty
            )
            password != confirmPassword -> context.getString(
                R.string.error_incorrectly_repeated_password
            )
            username.length < MIN_USERNAME_LENGTH -> context.getString(
                R.string.error_username_too_short, MIN_USERNAME_LENGTH
            )
            username.length > MAX_USERNAME_LENGTH -> context.getString(
                R.string.error_username_too_long, MAX_USERNAME_LENGTH
            )
            password.length < MIN_PASSWORD_LENGTH -> context.getString(
                R.string.error_password_too_short, MIN_PASSWORD_LENGTH
            )
            (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) -> context.getString(
                R.string.error_not_a_valid_email
            )

            else -> null
        }

        error?.let {
            _registerStatus.postValue(Event(Resource.Error(it)))
            return
        }
        _registerStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch() {
            val result = repository.register(email, username, password)
            _registerStatus.postValue(Event(result))
        }
    }
}

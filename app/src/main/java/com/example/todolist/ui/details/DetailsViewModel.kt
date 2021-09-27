package com.example.todolist.ui.details

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.repository.NoteRepositoryImpl
import com.example.todolist.utils.Event
import com.example.todolist.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {


    @SuppressLint("StaticFieldLeak")

    lateinit var repository: NoteRepositoryImpl
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main

    private val _updateNoteStatus = MutableLiveData<Event<Resource<Any>>>()
    val updateNoteStatus: LiveData<Event<Resource<Any>>> = _updateNoteStatus

    fun updateNote(title: String, note: String) {
        repository = NoteRepositoryImpl()
        _updateNoteStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.updateNote(title, note)
            _updateNoteStatus.postValue(Event(result))
        }

    }

}
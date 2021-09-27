package com.example.todolist.ui.details

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.R
import com.example.todolist.data.Note
import com.example.todolist.data.NoteUpdate
import com.example.todolist.repository.NoteRepositoryImpl
import com.example.todolist.utils.Event
import com.example.todolist.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {


    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    lateinit var repository: NoteRepositoryImpl
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main

    fun init(c: Context) {
        context = c
    }

    private val _updateNoteStatus = MutableLiveData<Event<Resource<Any>>>()
    val updateNoteStatus: LiveData<Event<Resource<Any>>> = _updateNoteStatus

    fun updateNote(noteUpdate: NoteUpdate) {
        repository = NoteRepositoryImpl()
        _updateNoteStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.updateNote(noteUpdate)
            _updateNoteStatus.postValue(Event(result))
        }

    }
}
package com.example.todolist.ui.create_note

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.R
import com.example.todolist.repository.NoteRepositoryImpl
import com.example.todolist.utils.Event
import com.example.todolist.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateNoteViewModel : ViewModel() {

    private val _createNoteStatus = MutableLiveData<Event<Resource<Any>>>()
    val createNoteStatus: LiveData<Event<Resource<Any>>> = _createNoteStatus

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    lateinit var repository: NoteRepositoryImpl
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main

    fun init(c: Context) {
        context = c
    }

    fun createNote(title: String, note: String) {
        repository = NoteRepositoryImpl()
        if (note.isEmpty()) {
            val error = context.getString(R.string.error_input_empty)
            _createNoteStatus.postValue(Event(Resource.Error(error)))
        } else {
            _createNoteStatus.postValue(Event(Resource.Loading()))
            viewModelScope.launch(dispatcher) {
                val result = repository.createNote(title, note)
                _createNoteStatus.postValue(Event(result))
            }
        }
    }
}
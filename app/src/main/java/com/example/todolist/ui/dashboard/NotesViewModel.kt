package com.example.todolist.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.Note
import com.example.todolist.repository.NoteRepositoryImpl
import com.example.todolist.utils.Event
import com.example.todolist.utils.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    lateinit var repository: NoteRepositoryImpl
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main

    fun init(c: Context) {
        context = c
    }

    private val _doneNoteStatus = MutableLiveData<Event<Resource<Boolean>>>()
    val likePostStatus: LiveData<Event<Resource<Boolean>>> = _doneNoteStatus

    private val _deleteNoteStatus = MutableLiveData<Event<Resource<Note>>>()
    val deletePostStatus: LiveData<Event<Resource<Note>>> = _deleteNoteStatus


    fun toggleCheckIconForNote(note: Note) {
        _doneNoteStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.toggleDoneIconForNote(note)
            _doneNoteStatus.postValue(Event(result))
        }
    }


    fun deleteNote(note: Note) {
        _deleteNoteStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.deleteNote(note)
            _deleteNoteStatus.postValue(Event(result))
        }
    }
}
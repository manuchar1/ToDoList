package com.example.todolist.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.todolist.data.Note
import com.example.todolist.data.NotesPagingSource
import com.example.todolist.data.User
import com.example.todolist.repository.NoteRepositoryImpl
import com.example.todolist.utils.Constants.PAGE_SIZE
import com.example.todolist.utils.Event
import com.example.todolist.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesViewModel : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    lateinit var repository: NoteRepositoryImpl
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main

    fun init(c: Context) {
        context = c
    }


    private val _deleteNoteStatus = MutableLiveData<Event<Resource<Note>>>()
    val deleteNoteStatus: LiveData<Event<Resource<Note>>> = _deleteNoteStatus


    fun getPagingFlow(uid: String): Flow<PagingData<Note>> {
        val pagingSource = NotesPagingSource(
            FirebaseFirestore.getInstance(),
            uid
        )
        return Pager(PagingConfig(PAGE_SIZE)) {
            pagingSource
        }.flow.cachedIn(viewModelScope)
    }


    fun deleteNote(note: Note) {
        repository = NoteRepositoryImpl()
        _deleteNoteStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.deleteNote(note)
            _deleteNoteStatus.postValue(Event(result))
        }
    }
}
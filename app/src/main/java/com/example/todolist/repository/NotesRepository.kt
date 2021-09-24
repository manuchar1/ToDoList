package com.example.todolist.repository
import com.example.todolist.data.Note
import com.example.todolist.data.NoteUpdate
import com.example.todolist.data.User
import com.example.todolist.utils.Resource

interface NotesRepository {

    suspend fun createNote(tittle: String, note: String): Resource<Any>
    suspend fun deleteNote(note: Note): Resource<Note>
    suspend fun updateNote(noteUpdate: NoteUpdate): Resource<Any>
    suspend fun toggleDoneIconForNote(note: Note): Resource<Boolean>
    suspend fun getUser(uid: String): Resource<User>
}
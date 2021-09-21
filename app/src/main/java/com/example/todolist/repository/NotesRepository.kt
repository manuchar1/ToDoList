package com.example.todolist.repository
import com.example.todolist.data.Note
import com.example.todolist.data.NoteUpdate
import com.example.todolist.utils.Resource

interface NotesRepository {

    suspend fun createNote(title: String, note: String): Resource<Any>
    suspend fun deleteNote(note: Note): Resource<Note>
    suspend fun updateNote(noteUpdate: NoteUpdate): Resource<Any>
    suspend fun toggleLikeForNote(post: Note): Resource<Boolean>
}
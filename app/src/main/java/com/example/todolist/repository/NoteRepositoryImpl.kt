package com.example.todolist.repository

import com.example.todolist.app.safeCall
import com.example.todolist.data.Note
import com.example.todolist.data.NoteUpdate
import com.example.todolist.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class NoteRepositoryImpl : NotesRepository {

    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private val storage = Firebase.storage
    private val users = fireStore.collection("users")
    private val notes = fireStore.collection("notes")


    override suspend fun createNote(title: String, note: String) = withContext(Dispatchers.IO) {
        safeCall {
            val uid = auth.uid!!
            val postId = UUID.randomUUID().toString()
            val notess = Note(
                id = postId,
                authorUid = uid,
                title = title,
                note = note,
                date = System.currentTimeMillis()
            )
            notes.document(postId).set(notess).await()
            Resource.Success(Any())
        }
    }

    override suspend fun deleteNote(note: Note): Resource<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(noteUpdate: NoteUpdate): Resource<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleLikeForNote(post: Note): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}
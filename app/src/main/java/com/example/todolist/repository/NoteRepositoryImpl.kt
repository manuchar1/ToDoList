package com.example.todolist.repository

import com.example.todolist.app.safeCall
import com.example.todolist.data.Note
import com.example.todolist.data.NoteUpdate
import com.example.todolist.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*

class NoteRepositoryImpl : NotesRepository {

    private val auth = FirebaseAuth.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()
    private val notes = fireStore.collection("notes")


    override suspend fun createNote(tittle: String, note: String) = withContext(Dispatchers.IO) {
        safeCall {
            val uid = auth.uid!!
            val postId = UUID.randomUUID().toString()
            val notess = Note(
                id = postId,
                authorUid = uid,
                title = tittle,
                note = note,
                date = System.currentTimeMillis()
            )
            notes.document(postId).set(notess).await()
            Resource.Success(Any())
        }
    }

    override suspend fun deleteNote(note: Note) = withContext(Dispatchers.IO) {
        safeCall {
            notes.document(note.id).delete().await()
            Resource.Success(note)
        }
    }



   suspend fun updateNote(tittle: String, note: String) = withContext(Dispatchers.IO) {
        safeCall {
            val uid = auth.uid!!
            val postId = UUID.randomUUID().toString()
            val notess = Note(
                id = postId,
                authorUid = uid,
                title = tittle,
                note = note,
                date = System.currentTimeMillis()
            )
          //  notes.document(postId).set(notess).await()
            notes.document(postId).update("title",notess.id).await()
            Resource.Success(Any())
        }
    }
}
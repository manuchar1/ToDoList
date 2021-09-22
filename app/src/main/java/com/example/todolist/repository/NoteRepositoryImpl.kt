package com.example.todolist.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.todolist.app.safeCall
import com.example.todolist.data.Note
import com.example.todolist.data.NoteUpdate
import com.example.todolist.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
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



    override suspend fun updateNote(noteUpdate: NoteUpdate): Resource<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun toggleDoneIconForNote(note: Note): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    val postListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            val post = dataSnapshot.getValue<Note>()

            // ...
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
        }
    }
   // postReference.addValueEventListener(postListener)


    /* override suspend fun toggleDoneIconForNote(note: Note)= withContext(Dispatchers.IO) {
         safeCall {
             var isLiked = false
             fireStore.runTransaction { transaction ->
                 val uid = FirebaseAuth.getInstance().uid!!
                 val postResult = transaction.get(notes.document(note.id))
                 val currentLikes = postResult.toObject(Note::class.java)?.likedBy ?: listOf()
                 transaction.update(
                     notes.document(note.id),
                     "likedBy",
                     if (uid in currentLikes) currentLikes - uid else {
                         isLiked = true
                         currentLikes + uid
                     }
                 )
             }.await()
             Resource.Success(isLiked)
         }
     }*/
}
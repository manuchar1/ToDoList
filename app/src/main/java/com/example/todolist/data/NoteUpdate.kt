package com.example.todolist.data

import com.google.firebase.firestore.CollectionReference


data class NoteUpdate(
    val authorUid: String? = "",
    val tittle: String = "",
    val note: String = "",
    val date: Long = 0L,
   // var likedBy: List<String> = listOf()
)
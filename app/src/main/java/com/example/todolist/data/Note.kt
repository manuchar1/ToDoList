package com.example.todolist.data

import com.google.firebase.firestore.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class Note(
    val id: String = "",
    val authorUid: String = "",
    var title: String = "",
    val note: String = "",
    val date: Long = 0L,
) : Serializable
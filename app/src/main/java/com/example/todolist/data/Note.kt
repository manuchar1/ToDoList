package com.example.todolist.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.Query
import java.io.Serializable

@IgnoreExtraProperties
data class Note(
  //  @get:Exclude var idr: Int? = null,
    val id: String = "",
    val authorUid: String = "",
    var title: String = "",
    val note: String = "",
    val date: Long = 0L,
    @get:Exclude var isLiked: Boolean = false,
    @get:Exclude var isLiking: Boolean = false,
   // var likedBy: List<String> = listOf()

) : Serializable
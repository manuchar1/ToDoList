package com.example.todolist.data


import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val uid: String = "",
    val username: String = "",
    val description: String = "",
)
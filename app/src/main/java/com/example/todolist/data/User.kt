package com.example.todolist.data


import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val uid: String = "",
    val username: String = "",
  //  val profilePictureUrl: String = DEFAULT_PROFILE_PICTURE_URL,
    val description: String = "",
)
package com.example.todolist.data


data class NoteUpdate(
    val authorUid: String? = "",
    val tittle: String = "",
    val note: String = "",
    val date: Long = 0L,
)
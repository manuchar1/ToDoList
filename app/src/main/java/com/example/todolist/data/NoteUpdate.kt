package com.example.todolist.data



data class NoteUpdate(
    val uidToUpdate: String = "",
    val title: String = "",
    val note: String = "",
)
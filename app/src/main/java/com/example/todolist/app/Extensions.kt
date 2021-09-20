package com.example.todolist.app


import com.example.todolist.utils.Resource


inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {

    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An unknown error occured")
    }
}




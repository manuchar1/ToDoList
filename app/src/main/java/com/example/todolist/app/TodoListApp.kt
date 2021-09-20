package com.example.todolist.app

import android.app.Application
import android.content.Context
import com.example.todolist.utils.DataStore

class TodoListApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DataStore.initialize(this, getSharedPreferences("_sp_", Context.MODE_PRIVATE))
    }
}
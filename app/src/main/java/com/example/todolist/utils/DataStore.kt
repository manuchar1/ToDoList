package com.example.todolist.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.todolist.utils.Constants.LOGIN_TOKEN
import java.util.*

object DataStore {

    private const val KEY_LANGUAGE = "key_language"

    private var sharedPreferences: SharedPreferences? = null



    fun initialize(context: Context, sharedPreferences: SharedPreferences) {
        DataStore.sharedPreferences = sharedPreferences
    }

    var language: String
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putString(KEY_LANGUAGE, value)?.commit()
        }
        get() {
            return sharedPreferences?.getString(KEY_LANGUAGE, Locale.getDefault().language)
                ?: throw RuntimeException("not initialized!!")
        }


  /*  var authToken: String?
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putString(LOGIN_TOKEN, value)?.commit()
        }
        get() {
            return (sharedPreferences ?: throw RuntimeException("not initialized!!"))
                .getString(LOGIN_TOKEN, null)
        }*/

    fun saveToken(token:String) {
        sharedPreferences?.edit()?.putString(LOGIN_TOKEN,token)?.apply()
    }

    fun token() = sharedPreferences?.getString(LOGIN_TOKEN,"")

}
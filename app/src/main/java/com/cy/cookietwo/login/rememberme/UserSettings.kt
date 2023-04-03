package com.cy.cookietwo.login.rememberme

import android.content.Context
import android.content.SharedPreferences

class UserSettings {
    private val sharedPrefFile = "loginFile"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private val SOME_KEY_NUMBER: String = "number"
    private val SOME_KEY_EMAIL: String = "email"
    private val SOME_KEY_PASSWORD: String = "password"

    fun instancePref(context: Context) {
        sharedPreferences = context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun setNumber(value: Int) {
        editor.putInt(SOME_KEY_NUMBER, value)
        editor.apply()
        editor.commit()
    }

    fun setPassword(value: String){
        editor.putString(SOME_KEY_PASSWORD, value)
        editor.apply()
        editor.commit()
    }

    fun setEmail(value: String){
        editor.putString(SOME_KEY_EMAIL, value)
        editor.apply()
        editor.commit()
    }

    fun getEmail(): String? {
        var sharedValue = sharedPreferences.getString(SOME_KEY_EMAIL, "")
        return sharedValue
    }

    fun getNumber(): Int {
        var sharedValue = sharedPreferences.getInt(SOME_KEY_NUMBER, 0)
        return sharedValue
    }

    fun getPassword(): String? {
        var sharedValue = sharedPreferences.getString(SOME_KEY_PASSWORD, "")
        return sharedValue
    }
}
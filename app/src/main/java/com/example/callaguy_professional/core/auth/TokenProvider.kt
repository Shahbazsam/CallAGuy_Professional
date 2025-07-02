package com.example.callaguy_professional.core.auth

import android.content.SharedPreferences
import androidx.core.content.edit

interface TokenProvider {
    fun getToken() : String?
    fun saveToken(token : String )
    fun clearToken()
}


class SharedPrefTokenProvider(
    private val sharedPreferences: SharedPreferences
) : TokenProvider {

    override fun clearToken() {
        sharedPreferences.edit { remove("jwt") }
    }

    override fun getToken(): String? {
        val prefs = sharedPreferences.getString("jwt", null)
        return prefs
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit {
            putString("jwt" , token)
        }
    }
}
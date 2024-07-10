package com.example.createtogether.utility

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import java.util.UUID

object UserUtil {
    private const val userIdKey = "userId"
    private const val userNameKey = "userName"

    fun getUserId(context: Context): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        var userId: String? = sharedPreferences.getString(userIdKey, null)

        if (userId == null) {
            userId = generateUserId()
            sharedPreferences.edit().putString(userIdKey, userId).apply()
        }

        return userId
    }

    fun setDefaultUserName(context: Context): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        var userName: String? = sharedPreferences.getString(userNameKey, null)

        if (userName == null) {
            userName = "Random User"
            sharedPreferences.edit().putString(userNameKey, userName).apply()
        }

        return userName
    }



    private fun generateUserId(): String {
        return UUID.randomUUID().toString()
    }
}

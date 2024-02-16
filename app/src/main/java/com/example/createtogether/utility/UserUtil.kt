package com.example.createtogether.utility

import android.content.Context
import android.content.SharedPreferences
import java.util.UUID

object UserUtil {
    private const val USER_ID_KEY = "userId"

    fun getUserId(context: Context): String {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        var userId: String? = sharedPreferences.getString(USER_ID_KEY, null)

        if (userId == null) {
            userId = generateUserId()
            sharedPreferences.edit().putString(USER_ID_KEY, userId).apply()
        }

        return userId
    }

    private fun generateUserId(): String {
        return UUID.randomUUID().toString()
    }
}

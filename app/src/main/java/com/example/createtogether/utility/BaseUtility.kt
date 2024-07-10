package com.example.createtogether.utility

import android.content.Context
import android.widget.Toast

object BaseUtility {

    fun showToast(context: Context, text: String){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}
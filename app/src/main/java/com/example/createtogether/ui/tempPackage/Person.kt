package com.example.createtogether.ui.tempPackage

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class Person(
    val name: String,
    val age: Int
): Parcelable

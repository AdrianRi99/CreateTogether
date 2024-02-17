package com.example.createtogether.db.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TextContent(
    val creatorId: String,
    val creator: String,
    val textId: String,
    val textTitle: String,
    val text: String
) : Parcelable
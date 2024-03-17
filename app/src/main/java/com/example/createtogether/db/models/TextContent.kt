package com.example.createtogether.db.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "TextsTable")
data class TextContent(
    @ColumnInfo(name = "creatorId") val creatorId: String,
    @ColumnInfo(name = "creator") val creator: String,
    @PrimaryKey val textId: String,
    @ColumnInfo(name = "textAuthenticator") val textAuthenticator: String,
    @ColumnInfo(name = "textTitle") val textTitle: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "contributors") val contributors: String?,
    @ColumnInfo(name = "likes") val likes: Int,
    @ColumnInfo(name = "status") val status: String
    ) : Parcelable
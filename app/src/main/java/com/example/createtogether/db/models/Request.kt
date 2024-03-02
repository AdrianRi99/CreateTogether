package com.example.createtogether.db.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Request")
data class Request(
    @PrimaryKey val requestId: String,
    @ColumnInfo(name = "requestStatus") val requestStatus: String,
    @ColumnInfo(name = "requestCreator") val requestCreator: String,
    @ColumnInfo(name = "textId") val textId: String,
    @ColumnInfo(name = "modifiedTextTitle") val modifiedTextTitle: String,
    @ColumnInfo(name = "modifiedText") val modifiedText: String
    ) : Parcelable
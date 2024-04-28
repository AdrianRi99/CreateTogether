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


//From CA-T
//@Entity(tableName = "challengeTable")
//class Challenge (@ColumnInfo(name = "goalOfApproaches") val goalOfApproaches :String,
//                 @ColumnInfo(name = "actualApproaches") val actualApproaches :String,
//                 @ColumnInfo(name = "totalSuccesses") val totalSuccesses :String,
//                 @ColumnInfo(name = "totalHesitations") val totalHesitations :String,
//                 @ColumnInfo(name = "dateChallengeCreated") val dateChallengeCreated :String,
//                 @ColumnInfo(name = "timeForChallenge") val timeForChallenge :String){
//    @PrimaryKey(autoGenerate = true)
//    var id = 0
//}
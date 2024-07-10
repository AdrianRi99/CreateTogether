package com.example.createtogether.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.createtogether.db.daos.TextContentDao
import com.example.createtogether.db.models.TextContent


@Database(entities = [TextContent::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class DatabaseApp : RoomDatabase() {

    abstract fun getTextContentDao(): TextContentDao

}




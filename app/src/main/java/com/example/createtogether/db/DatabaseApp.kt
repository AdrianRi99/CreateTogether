package com.example.createtogether.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.createtogether.db.daos.TextContentDao
import com.example.createtogether.db.models.TextContent


@Database(entities = [TextContent::class], version = 1, exportSchema = false)
abstract class DatabaseApp : RoomDatabase() {

    abstract fun getTextContentDao(): TextContentDao

}
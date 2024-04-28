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



//From CA-T
//abstract fun getNotesDao(): ReportDao
//
//abstract fun getChallengeDao(): ChallengeDao
//
//companion object {
//    // Singleton prevents multiple instances of database opening at the
//    // same time - would be very bad for performance
//    @Volatile
//    private var INSTANCE: DatabaseApp? = null
//
//    fun getDatabase(context: Context): DatabaseApp {
//        // if the INSTANCE is not null, then return it,
//        // if it is, then create the database
//        return INSTANCE ?: synchronized(this) {
//            val instance = Room.databaseBuilder(
//                context.applicationContext,
//                DatabaseApp::class.java,
//                "database"
//            ).build()
//            INSTANCE = instance
//            // return instance
//            return instance
//        }
//    }
//}
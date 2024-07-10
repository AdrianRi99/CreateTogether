package com.example.createtogether.db.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.createtogether.db.models.TextContent

@Dao
interface TextContentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)  //if you add exactly the same note
    suspend fun insert(textContent: TextContent) //suspend -> because of Kotlin coroutines

    @Update
    suspend fun update(textContent: TextContent)


    @Delete
    suspend fun delete(textContent: TextContent)

    @Query("Select * from TextsTable")
    fun getAllTexts(): LiveData<List<TextContent>>



    @Query("SELECT * FROM TextsTable WHERE textId = :textId")
    fun getTextById(textId: String): TextContent?

}
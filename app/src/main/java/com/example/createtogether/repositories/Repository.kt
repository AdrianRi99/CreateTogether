package com.example.createtogether.repositories

import androidx.lifecycle.LiveData
import com.example.createtogether.db.daos.TextContentDao
import com.example.createtogether.db.models.TextContent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val textContentDao: TextContentDao
    ){

    val allTexts : LiveData<List<TextContent>> = textContentDao.getAllTexts()

    suspend fun insert(textContent: TextContent) {
        textContentDao.insert(textContent)
    }

    suspend fun update(textContent: TextContent) {
        textContentDao.update(textContent)
    }



    suspend fun delete(textContent: TextContent) {
        textContentDao.delete(textContent)
    }

    suspend fun getTextById(textId: String): TextContent? {
        return textContentDao.getTextById(textId)
    }


}
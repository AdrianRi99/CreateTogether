package com.example.createtogether.ui.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository  //even tough we do not have mainRepository provides function in the AppModule, injecting works because MainRepository
): ViewModel() {                //only needs RunDao as a Parameter, and Dagger knows how to create a RunDao - MAGIC

    val allTexts : LiveData<List<TextContent>> = repository.allTexts

    fun addText(textContent: TextContent) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        repository.insert(textContent)
    }

    fun updateText(textContent: TextContent) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        repository.update(textContent)
    }

    fun deleteText(textContent: TextContent) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        repository.delete(textContent)
    }

    fun getTextById(textId: String): LiveData<TextContent?> {
        val resultLiveData = MutableLiveData<TextContent?>()

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getTextById(textId)
            withContext(Dispatchers.Main) {
                resultLiveData.value = result
            }
        }

        return resultLiveData
    }

}
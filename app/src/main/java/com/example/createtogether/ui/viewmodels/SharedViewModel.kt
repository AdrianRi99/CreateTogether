package com.example.createtogether.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.createtogether.db.models.TextContent

class SharedViewModel : ViewModel() {

    private val searchResults = MutableLiveData<MutableList<TextContent>>()
    private val selectedTextCategory = MutableLiveData<String>()

    fun setSearchResults(searchResults: MutableList<TextContent>) {
        this.searchResults.value = searchResults
    }

    fun getSearchResults(): LiveData<MutableList<TextContent>> = searchResults

    fun setSelectedTextCategory(selectedTextCategory: String) {
        this.selectedTextCategory.value = selectedTextCategory
    }

    fun getSelectedTextCategory(): LiveData<String> = selectedTextCategory
}

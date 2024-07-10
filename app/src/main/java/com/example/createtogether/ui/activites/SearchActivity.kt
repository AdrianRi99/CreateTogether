package com.example.createtogether.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.createtogether.databinding.ActivitySearchBinding
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchResults = intent.getParcelableArrayListExtra<TextContent>("searchResults")
            ?.toMutableList()

        val selectedTextCategory = intent.getStringExtra("selectedTextCategory") ?: "Undefined"

        if (searchResults != null) {
            sharedViewModel.setSearchResults(searchResults)
            sharedViewModel.setSelectedTextCategory(selectedTextCategory)
        }

    }
}


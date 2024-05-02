package com.example.createtogether.ui.tempPackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.createtogether.R
import com.example.createtogether.databinding.ActivitySavedTextsBinding
import com.example.createtogether.databinding.ActivitySearchBinding
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize

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


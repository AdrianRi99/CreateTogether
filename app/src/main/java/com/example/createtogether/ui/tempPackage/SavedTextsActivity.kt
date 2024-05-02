package com.example.createtogether.ui.tempPackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.createtogether.R
import com.example.createtogether.databinding.ActivityRequestsBinding
import com.example.createtogether.databinding.ActivitySavedTextsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedTextsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySavedTextsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedTextsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
package com.example.createtogether.ui.activites

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.createtogether.R
import com.example.createtogether.databinding.ActivityRequestsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}
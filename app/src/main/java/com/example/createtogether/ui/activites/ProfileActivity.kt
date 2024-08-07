package com.example.createtogether.ui.activites

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.createtogether.databinding.ActivityProfileBinding
import com.example.createtogether.utility.BaseUtility

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val savedUsername = sharedPreferences.getString("userName", "") ?: "Not found"
        binding.editTextUsername.setText(savedUsername)

        binding.btnSaveUserName.setOnClickListener {
            val userName = binding.editTextUsername.text.toString().trim()

            if (userName.isEmpty()) {
                BaseUtility.showToast(this, "Enter a username")
            } else {
                with(sharedPreferences.edit()) {
                    putString("userName", userName)
                    apply()
                }

                BaseUtility.showToast(this, "Saved state")
            }
        }
    }
}
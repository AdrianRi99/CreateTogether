package com.example.createtogether.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentCreateContentBinding
import com.example.createtogether.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val savedUsername = sharedPreferences.getString("userName", "") ?: "Not found"
        binding.editTextUsername.setText(savedUsername)

        binding.btnSaveUserName.setOnClickListener {
            val userName = binding.editTextUsername.text.toString().trim()

            with(sharedPreferences.edit()) {
                putString("userName", userName)
                apply()
            }
        }
    }
}
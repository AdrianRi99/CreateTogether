package com.example.createtogether.ui.fragments.createcontent

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentCreateContentBinding

class CreateContentFragment : Fragment(R.layout.fragment_create_content) {

    private lateinit var binding: FragmentCreateContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateContentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartWriting.setOnClickListener {
            val selectedTextCategory = binding.spinnerTextCategories.selectedItem.toString()
            val action = CreateContentFragmentDirections.actionCreateContentFragmentToNewTextFragment(selectedTextCategory)
            findNavController().navigate(action)
        }

        binding.btnOpenSavedTexts.setOnClickListener {
            val action = CreateContentFragmentDirections.actionCreateContentFragmentToOpenSavedTextsFragment()
            findNavController().navigate(action)
        }
    }
}
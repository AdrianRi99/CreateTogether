package com.example.createtogether.ui.fragments.createcontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentCreateContentBinding
import com.example.createtogether.databinding.FragmentOpenSavedTextsBinding

class OpenSavedTextsFragment : Fragment(R.layout.fragment_open_saved_texts) {

    private lateinit var binding: FragmentOpenSavedTextsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOpenSavedTextsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            val action = OpenSavedTextsFragmentDirections.actionOpenSavedTextsFragmentToCreateContentFragment()
            findNavController().navigate(action)
        }
    }
}
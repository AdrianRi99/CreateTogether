package com.example.createtogether.ui.tempPackage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentTestBinding
import com.example.createtogether.ui.viewmodels.SharedViewModel

class TestFragment : Fragment(R.layout.fragment_test) {

    private lateinit var binding: FragmentTestBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTestBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.getSearchResults().observe(viewLifecycleOwner) { searchResults ->
            searchResults.forEach {
                Log.d("HoHo", "Fragment2 ${it.textTitle}")
                Log.d("HoHo", "")

            }
        }

    }

}
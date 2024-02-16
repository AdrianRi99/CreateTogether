package com.example.createtogether.ui.fragments.createcontent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentCreateContentBinding
import com.example.createtogether.databinding.FragmentNewTextBinding

class NewTextFragment : Fragment(R.layout.fragment_new_text) {

    private lateinit var binding: FragmentNewTextBinding
    private val args: NewTextFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewTextBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedTextCategory = args.selectedTextCategory
        Log.d("Oha", selectedTextCategory)


        binding.btnBack.setOnClickListener {
            val action = NewTextFragmentDirections.actionNewTextFragmentToCreateContentFragment()
            findNavController().navigate(action)
        }
    }
}
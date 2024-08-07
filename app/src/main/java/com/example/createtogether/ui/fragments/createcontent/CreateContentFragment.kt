package com.example.createtogether.ui.fragments.createcontent

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentCreateContentBinding
import com.example.createtogether.ui.activites.ProfileActivity
import com.example.createtogether.ui.activites.WriteTextActivity
import com.example.createtogether.ui.activites.RequestsActivity
import com.example.createtogether.ui.activites.SavedTextsActivity

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

        binding.btnRequests.setOnClickListener {
            val intent = Intent(requireContext(), RequestsActivity::class.java)
            startActivity(intent)
        }

        binding.iVProfile.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnStartWriting.setOnClickListener {
            val selectedTextCategory = binding.spinnerTextCategories.selectedItem.toString()

            val intent = Intent(requireContext(), WriteTextActivity::class.java)
            intent.putExtra("SelectedTextCategory", selectedTextCategory)
            startActivity(intent)
//            val action = CreateContentFragmentDirections.actionCreateContentFragmentToNewTextFragment(selectedTextCategory)
//            findNavController().navigate(action)
        }

        binding.btnOpenSavedTexts.setOnClickListener {
//            val action = CreateContentFragmentDirections.actionCreateContentFragmentToOpenSavedTextsFragment()
//            findNavController().navigate(action)

            val intent = Intent(requireContext(), SavedTextsActivity::class.java)
            startActivity(intent)
        }
    }
}
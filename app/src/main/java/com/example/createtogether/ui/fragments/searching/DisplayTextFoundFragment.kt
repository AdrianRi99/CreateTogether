package com.example.createtogether.ui.fragments.searching

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentDisplayTextFoundBinding
import com.example.createtogether.utility.UserUtil
import com.google.firebase.database.FirebaseDatabase

class DisplayTextFoundFragment : Fragment(R.layout.fragment_display_text_found) {

    private lateinit var binding: FragmentDisplayTextFoundBinding
    private val args: DisplayTextFoundFragmentArgs by navArgs()

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val categoriesReference = databaseReference.child("categories")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDisplayTextFoundBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textFound = args.textFound

        binding.tvTextFoundTitle.text = textFound.textTitle
        binding.tvTextFound.text = textFound.text

        binding.switchChangeToEditMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tvTextFoundTitle.visibility = View.GONE
                binding.tvTextFound.visibility = View.GONE
                binding.scrollViewTextFound.visibility = View.GONE
                binding.tvContributers.visibility = View.GONE
                binding.iVLike.visibility = View.GONE

                binding.editTextTextFoundTitle.visibility = View.VISIBLE
                binding.editTextTextFound.visibility = View.VISIBLE
                binding.btnViewChanges.visibility = View.VISIBLE

                binding.editTextTextFoundTitle.setText(textFound.textTitle)
                binding.editTextTextFound.setText(textFound.text)
            } else {
                binding.tvTextFoundTitle.visibility = View.VISIBLE
                binding.tvTextFound.visibility = View.VISIBLE
                binding.scrollViewTextFound.visibility = View.VISIBLE
                binding.tvContributers.visibility = View.VISIBLE
                binding.iVLike.visibility = View.VISIBLE

                binding.editTextTextFoundTitle.visibility = View.GONE
                binding.editTextTextFound.visibility = View.GONE
                binding.btnViewChanges.visibility = View.GONE
            }
        }

        binding.tvContributers.setOnClickListener {
            if(textFound.contributors.isNullOrBlank()) {
                Toast.makeText(requireActivity(), "This text has no contributers", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireActivity(), "Contributors: ${textFound.contributors}", Toast.LENGTH_LONG).show()
            }
        }

        binding.iVLike.setOnClickListener {
            Toast.makeText(requireActivity(), "+1 Like", Toast.LENGTH_LONG).show()

            var likes = textFound.likes
            likes++
            Log.d("HoHo", likes.toString())
            categoriesReference.child(textFound.category).child(textFound.textId).child("likes").setValue(likes)

        }

        binding.btnViewChanges.setOnClickListener {
//            if(textFound.creatorId == UserUtil.getUserId(requireActivity())){
//                Toast.makeText(requireActivity(), "You are the creator of this text", Toast.LENGTH_LONG).show()
//            }

            val modifiedTextTitle = binding.editTextTextFoundTitle.text.toString()
            val modifiedText = binding.editTextTextFound.text.toString()

            val action = DisplayTextFoundFragmentDirections.actionDisplayTextFoundFragmentToViewChangesTextFoundFragment(textFound, modifiedTextTitle, modifiedText)
            findNavController().navigate(action)
        }

//        binding.btnBack.setOnClickListener {
//            findNavController().popBackStack()
//        }

    }
}
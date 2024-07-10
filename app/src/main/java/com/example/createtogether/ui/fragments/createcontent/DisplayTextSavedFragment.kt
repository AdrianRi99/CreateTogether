package com.example.createtogether.ui.fragments.createcontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentDisplayTextFoundBinding
import com.example.createtogether.databinding.FragmentDisplayTextSavedBinding
import com.example.createtogether.ui.fragments.searching.DisplayTextFoundFragmentDirections
import com.example.createtogether.utility.BaseUtility
import com.example.createtogether.utility.UserUtil

class DisplayTextSavedFragment : Fragment(R.layout.fragment_display_text_saved) {

    private lateinit var binding: FragmentDisplayTextSavedBinding
    private val args: DisplayTextSavedFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDisplayTextSavedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textSaved = args.textSaved

        binding.tvTextSavedTitle.text = textSaved.textTitle
        binding.tvTextSaved.text = textSaved.text

        binding.switchChangeToEditMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tvTextSavedTitle.visibility = View.GONE
                binding.tvTextSaved.visibility = View.GONE
                binding.scrollViewTextFound.visibility = View.GONE
                binding.tvContributers.visibility = View.GONE
                binding.view2.visibility = View.GONE


                binding.editTextTextSavedTitle.visibility = View.VISIBLE
                binding.editTextTextSaved.visibility = View.VISIBLE
                binding.btnViewChanges.visibility = View.VISIBLE

                binding.editTextTextSavedTitle.setText(textSaved.textTitle)
                binding.editTextTextSaved.setText(textSaved.text)
            } else {
                binding.tvTextSavedTitle.visibility = View.VISIBLE
                binding.tvTextSaved.visibility = View.VISIBLE
                binding.scrollViewTextFound.visibility = View.VISIBLE
                binding.tvContributers.visibility = View.VISIBLE
                binding.view2.visibility = View.VISIBLE


                binding.editTextTextSavedTitle.visibility = View.GONE
                binding.editTextTextSaved.visibility = View.GONE
                binding.btnViewChanges.visibility = View.GONE
            }
        }

        binding.tvContributers.setOnClickListener {
            if(textSaved.contributors.isNullOrBlank()) {
                Toast.makeText(requireActivity(), "This text has no contributers", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireActivity(), "Contributors: ${textSaved.contributors}", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnViewChanges.setOnClickListener {

            val modifiedTextTitle = binding.editTextTextSavedTitle.text.toString()
            val modifiedText = binding.editTextTextSaved.text.toString()

            if (modifiedTextTitle.isEmpty()) {
                BaseUtility.showToast(requireActivity(), "Enter a title")
            } else if (modifiedText.isEmpty()) {
                BaseUtility.showToast(requireActivity(), "The text-field can't be empty")
            } else {
                val action = DisplayTextSavedFragmentDirections.actionDisplayTextSavedFragmentToViewChangesTextSavedFragment(textSaved, modifiedTextTitle, modifiedText)
                findNavController().navigate(action)
            }
        }

//        binding.btnBack.setOnClickListener {
//            findNavController().popBackStack()
//        }

    }
}
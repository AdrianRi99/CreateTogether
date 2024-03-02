package com.example.createtogether.ui.fragments.createcontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.createtogether.R
import com.example.createtogether.adapters.TextContentSavedItemClickInterface
import com.example.createtogether.adapters.TextsSavedAdapter
import com.example.createtogether.databinding.FragmentCreateContentBinding
import com.example.createtogether.databinding.FragmentOpenSavedTextsBinding
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.fragments.searching.DisplayTextFoundFragmentDirections
import com.example.createtogether.ui.viewmodels.ViewModel
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenSavedTextsFragment : Fragment(R.layout.fragment_open_saved_texts), TextContentSavedItemClickInterface {

    private lateinit var binding: FragmentOpenSavedTextsBinding
    private lateinit var textsSavedAdapter: TextsSavedAdapter

    private val viewModel: ViewModel by viewModels()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val categoriesReference = databaseReference.child("categories")

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

        setupRecyclerView()

        viewModel.allTexts.observe(viewLifecycleOwner) { texts ->
            if(texts.toString() != "[]"){
                binding.textViewNoSavedTextsFound.text = ""
            }else{
                binding.textViewNoSavedTextsFound.text = "No Saved Texts found"
            }

            texts?.let {
                textsSavedAdapter.updateListOfSavedTexts(it)
            }
        }

        binding.btnBack.setOnClickListener {
            val action = OpenSavedTextsFragmentDirections.actionOpenSavedTextsFragmentToCreateContentFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewSavedTexts.apply {
        textsSavedAdapter = TextsSavedAdapter(this@OpenSavedTextsFragment)
        adapter = textsSavedAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(textContent: TextContent) {
        val action = OpenSavedTextsFragmentDirections.actionOpenSavedTextsFragmentToDisplayTextSavedFragment(textContent)
        findNavController().navigate(action)
    }

    override fun onLongClick(view: View, textContent: TextContent) {
        viewModel.deleteText(textContent)

        val categoryRef = categoriesReference.child(textContent.category).child(textContent.textId)
        categoryRef.removeValue()
    }
}
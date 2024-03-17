package com.example.createtogether.ui.fragments.createcontent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.createtogether.R
import com.example.createtogether.adapters.TextContentSavedItemClickInterface
import com.example.createtogether.adapters.TextsSavedAdapter
import com.example.createtogether.databinding.FragmentOpenSavedTextsBinding
import com.example.createtogether.databinding.FragmentShowVersionsOfSavedTextBinding
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.fragments.searching.SearchResultsFragmentArgs
import com.example.createtogether.ui.viewmodels.ViewModel
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowVersionsOfSavedTextFragment : Fragment(R.layout.fragment_show_versions_of_saved_text), TextContentSavedItemClickInterface {

    private lateinit var binding: FragmentShowVersionsOfSavedTextBinding
    private lateinit var textVersionsAdapter: TextsSavedAdapter
    private val args: ShowVersionsOfSavedTextFragmentArgs by navArgs()
    private val viewModel: ViewModel by viewModels()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val categoriesReference = databaseReference.child("categories")
    private lateinit var textVersions : MutableList<TextContent>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowVersionsOfSavedTextBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        textVersions = args.textVersions.toMutableList()
        textVersionsAdapter.updateListOfSavedTexts(textVersions)

        binding.tvShowNumberOfVersions.text = textVersions.size.toString()

        binding.btnBack.setOnClickListener {
            val action =
                ShowVersionsOfSavedTextFragmentDirections.actionShowVersionsOfSavedTextFragmentToOpenSavedTextsFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewShowVersionsOfText.apply {
        textVersionsAdapter = TextsSavedAdapter(this@ShowVersionsOfSavedTextFragment)
        adapter = textVersionsAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(textContent: TextContent) {
        val action = ShowVersionsOfSavedTextFragmentDirections.actionShowVersionsOfSavedTextFragmentToDisplayTextSavedFragment(textContent)
        findNavController().navigate(action)
    }

    override fun onLongClick(view: View, textContent: TextContent) {
        viewModel.deleteText(textContent)
        textVersions.remove(textContent)
        textVersionsAdapter.updateListOfSavedTexts(textVersions)

//        val categoryRef = categoriesReference.child(textContent.category).child(textContent.textId)
//        categoryRef.removeValue()
    }

}
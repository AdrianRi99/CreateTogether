package com.example.createtogether.ui.fragments.searching

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.createtogether.R
import com.example.createtogether.adapters.TextContentItemClickInterface
import com.example.createtogether.adapters.TextsFoundAdapter
import com.example.createtogether.databinding.FragmentSearchResultsBinding
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.viewmodels.SharedViewModel

class SearchResultsFragment : Fragment(R.layout.fragment_search_results), TextContentItemClickInterface {

    private lateinit var binding: FragmentSearchResultsBinding
//    private val args: SearchResultsFragmentArgs by navArgs()

    private lateinit var textsFoundAdapter: TextsFoundAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultsBinding.inflate(inflater,container,false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        sharedViewModel.getSelectedTextCategory().observe(viewLifecycleOwner) { selectedTextCategory ->
            binding.tvCategory.text = selectedTextCategory
        }

        sharedViewModel.getSearchResults().observe(viewLifecycleOwner) { searchResults ->
            searchResults.let { searchResult ->
                if(searchResult.isNotEmpty()) {
                    binding.textViewNoTextsFound.text = ""
                    val sortedList = searchResults.sortedByDescending { it.likes }
                    textsFoundAdapter.updateListOfTextsFound(sortedList)
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewTextsFound.apply {
        textsFoundAdapter = TextsFoundAdapter(this@SearchResultsFragment)
        adapter = textsFoundAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(textContent: TextContent) {
        Log.d("Oha", "Yap, geht")
        val action = SearchResultsFragmentDirections.actionSearchResultsFragmentToDisplayTextFoundFragment(textContent)
        findNavController().navigate(action)
    }
}
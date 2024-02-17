package com.example.createtogether.ui.fragments.searching

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.createtogether.R
import com.example.createtogether.adapters.TextContentItemClickInterface
import com.example.createtogether.adapters.TextsFoundAdapter
import com.example.createtogether.databinding.FragmentSearchBinding
import com.example.createtogether.databinding.FragmentSearchResultsBinding
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.fragments.createcontent.NewTextFragmentArgs
import org.w3c.dom.Text

class SearchResultsFragment : Fragment(R.layout.fragment_search_results), TextContentItemClickInterface {

    private lateinit var binding: FragmentSearchResultsBinding
    private val args: SearchResultsFragmentArgs by navArgs()

    private lateinit var textsFoundAdapter: TextsFoundAdapter


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
        val textsFound = args.textsFound.toMutableList()

        textsFound.let {
            if(it.isNotEmpty()) {
                binding.textViewNoTextsFound.text = ""
                textsFoundAdapter.updateListOfTextsFound(it)
            }
        }


        binding.btnBack.setOnClickListener {
            val action = SearchResultsFragmentDirections.actionSearchResultsFragmentToSearchFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewTextsFound.apply {
        textsFoundAdapter = TextsFoundAdapter(this@SearchResultsFragment)
        adapter = textsFoundAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(textContent: TextContent) {
        Log.d("Oha", "Yap, geht")

    }
}
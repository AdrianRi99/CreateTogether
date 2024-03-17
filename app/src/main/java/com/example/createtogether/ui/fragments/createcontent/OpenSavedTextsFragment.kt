package com.example.createtogether.ui.fragments.createcontent

import android.os.Bundle
import android.util.Log
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
class OpenSavedTextsFragment : Fragment(R.layout.fragment_open_saved_texts),
    TextContentSavedItemClickInterface {

    private lateinit var binding: FragmentOpenSavedTextsBinding
    private lateinit var textsSavedAdapter: TextsSavedAdapter

    private val viewModel: ViewModel by viewModels()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val categoriesReference = databaseReference.child("categories")

    private lateinit var groupedMap: Map<String, List<TextContent>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOpenSavedTextsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.allTexts.observe(viewLifecycleOwner) { texts ->
            if (texts.toString() != "[]") {
                binding.textViewNoSavedTextsFound.text = ""
            } else {
                binding.textViewNoSavedTextsFound.text = "No Saved Texts found"
            }


            groupedMap = texts.groupBy { it.textAuthenticator }

            val firstTextContentFromEachGroup: List<TextContent> = groupedMap
                .mapValues { (_, contents) -> contents.firstOrNull() } // Das erste TextContent-Objekt oder null
                .values
                .filterNotNull()

            textsSavedAdapter.updateListOfSavedTexts(firstTextContentFromEachGroup)

//            firstTextContentFromEachGroup.forEach {
//                Log.d("New", it.textAuthenticator)
//                Log.d("New", it.textTitle)
//                Log.d("New", "")
//            }


            //Logging
//            groupedMap.forEach { (authenticator, contents) ->
//                if (contents.isNotEmpty()) {
//
//                    contents.forEach { content ->
//                        Log.d("Test", content.textAuthenticator)
//                    }
//                    Log.d("Test", "")
//
//                } else {
//                    // Log für leere Gruppen
//                }
//            }
//            Log.d("Test", "")
//            groupedMap.forEach { (authenticator, contents) ->
//                Log.d("Test", authenticator)
//
//            }

//
//
//            texts?.let {
//                textsSavedAdapter.updateListOfSavedTexts(it)
//            }
        }

        binding.btnBack.setOnClickListener {
            val action =
                OpenSavedTextsFragmentDirections.actionOpenSavedTextsFragmentToCreateContentFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = binding.recyclerViewSavedTexts.apply {
        textsSavedAdapter = TextsSavedAdapter(this@OpenSavedTextsFragment)
        adapter = textsSavedAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(textContent: TextContent) {

        val textAuthenticator = textContent.textAuthenticator
        if (groupedMap.containsKey(textAuthenticator)) {
            val listOfTextContentForTheKey: List<TextContent> = groupedMap[textAuthenticator] ?: emptyList()

            if(listOfTextContentForTheKey.size == 1) {
                val singleTextContent = listOfTextContentForTheKey.first()
                val action =
                    OpenSavedTextsFragmentDirections.actionOpenSavedTextsFragmentToDisplayTextSavedFragment(
                        singleTextContent
                    )
                findNavController().navigate(action)
            } else {
                val action = OpenSavedTextsFragmentDirections.actionOpenSavedTextsFragmentToShowVersionsOfSavedTextFragment(listOfTextContentForTheKey.toTypedArray())
                findNavController().navigate(action)
            }

//            // Iteriere durch die Liste der TextContent-Objekte für den bestimmten Schlüssel
//            for (textContentInLIst in listOfTextContentForTheKey) {
//                Log.d("New", textContentInLIst.textAuthenticator)
//                Log.d("New", textContentInLIst.textTitle)
//                Log.d("New", "")
//            }
        }

    }

    override fun onLongClick(view: View, textContent: TextContent) {
        viewModel.deleteText(textContent)

        val categoryRef = categoriesReference.child(textContent.category).child(textContent.textId)
        categoryRef.removeValue()
    }
}
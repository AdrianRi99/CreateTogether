package com.example.createtogether.ui.fragments.searching

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.createtogether.R
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.databinding.FragmentSearchBinding
import com.example.createtogether.ui.tempPackage.Person
import com.example.createtogether.ui.tempPackage.SavedTextsActivity
import com.example.createtogether.ui.tempPackage.SearchActivity
import com.example.createtogether.ui.viewmodels.SharedViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val databaseReference = FirebaseDatabase.getInstance().reference

    private val categoriesReference = databaseReference.child("categories")
    private val textsFound = mutableListOf<TextContent>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {
            val selectedTextCategory = binding.spinnerTextCategories.selectedItem.toString()
            searchForTexts(selectedTextCategory)
        }
    }

    private fun searchForTexts(selectedTextCategory: String) {
        // Hier führen wir eine einmalige Abfrage auf dem "users" Knoten der Realtime Database durch
        categoriesReference.child(selectedTextCategory).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                textsFound.clear()
                for (userSnapshot in snapshot.children) {
                    val creatorId = userSnapshot.child("creatorId").getValue(String::class.java) ?: ""
                    val creator = userSnapshot.child("creator").getValue(String::class.java) ?: ""
                    val textId = userSnapshot.child("textId").getValue(String::class.java) ?: ""
                    val textAuthenticator = userSnapshot.child("textAuthenticator").getValue(String::class.java) ?: ""
                    val textTitle = userSnapshot.child("textTitle").getValue(String::class.java) ?: ""
                    val text = userSnapshot.child("text").getValue(String::class.java) ?: ""
                    val contributors = userSnapshot.child("contributors").getValue(String::class.java) ?: ""
                    val likes = userSnapshot.child("likes").getValue(Int::class.java) ?: 0
                    val status = userSnapshot.child("status").getValue(String::class.java) ?: ""
//                    if(creatorId != UserUtil.getUserId(requireActivity())) {  //später richig
//                        textsFound.add(TextContent(creatorId, creator, text))
//                    }
                    textsFound.add(TextContent(creatorId, creator, textId, textAuthenticator, textTitle, text, selectedTextCategory, contributors, likes, status))
                }

//                val action = SearchFragmentDirections.actionSearchFragmentToSearchResultsFragment(textsFound.toTypedArray(), selectedTextCategory)
//                findNavController().navigate(action)

                val intent = Intent(requireContext(), SearchActivity::class.java)
                intent.putParcelableArrayListExtra("searchResults", ArrayList(textsFound))
                intent.putExtra("selectedTextCategory", selectedTextCategory)
                startActivity(intent)


            }

            override fun onCancelled(error: DatabaseError) {
                // Handle den Fall, wenn die Datenbankabfrage abgebrochen wird
                Log.e("Firebase", "Database query cancelled: ${error.message}")
            }
        })
    }
}
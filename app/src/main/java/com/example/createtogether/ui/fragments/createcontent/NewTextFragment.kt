package com.example.createtogether.ui.fragments.createcontent

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.createtogether.R
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.databinding.FragmentNewTextBinding
import com.example.createtogether.ui.viewmodels.ViewModel
import com.example.createtogether.utility.UserUtil
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import kotlin.random.Random

@AndroidEntryPoint
class NewTextFragment : Fragment(R.layout.fragment_new_text) {

    private lateinit var binding: FragmentNewTextBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val args: NewTextFragmentArgs by navArgs()
    private val viewModel: ViewModel by viewModels()

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val categoriesReference = databaseReference.child("categories")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewTextBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedTextCategory = args.selectedTextCategory
        val savedUsername = sharedPreferences.getString("userName", "") ?: "Not found"
        val userId = UserUtil.getUserId(requireActivity())
        val textId =
            categoriesReference.push().key ?: "" // Generiere eine eindeutige ID für den Text

        val inspirationTerms = getInspirationTerms(requireActivity())

        binding.btnGenerateInspirations.setOnClickListener {

            val inspirationTermsText = createInspirationTermsText(inspirationTerms)
            binding.tvInspirationTerms.text = inspirationTermsText
            binding.tvInspirationTerms.visibility = View.VISIBLE
        }

        binding.btnSaveNewText.setOnClickListener {
            val enteredTextTitle = binding.editTextNewTextTitle.text.toString()
            val enteredText = binding.editTextNewText.text.toString()
//            Log.d("Oha", enteredText)
            val textAuthenticator = textId

            val newText = TextContent(
                userId,
                savedUsername,
                textId,
                textAuthenticator,
                enteredTextTitle,
                enteredText,
                selectedTextCategory,
                null,
                0,
                "Uploaded"
            )

            categoriesReference.child(selectedTextCategory).child(textId).setValue(newText)
            viewModel.addText(newText)

            val action = NewTextFragmentDirections.actionNewTextFragmentToCreateContentFragment()
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            val action = NewTextFragmentDirections.actionNewTextFragmentToCreateContentFragment()
            findNavController().navigate(action)
        }
    }

    private fun createInspirationTermsText(inspirationTerms: List<String>): String {

        // Drei zufällige, eindeutige Indizes auswählen
        val randomIndexes = mutableSetOf<Int>()
        while (randomIndexes.size < 4) {
            randomIndexes.add(Random.nextInt(0, inspirationTerms.size))
        }
        // Die ausgewählten Hunde in den resultierenden String einfügen, durch zwei Leerzeichen getrennt
        return randomIndexes.joinToString("  ") { inspirationTerms[it] }
    }

    private fun getInspirationTerms(context: Context): List<String> {
        val resId = R.raw.inspiration_terms
        val input: InputStream = context.resources.openRawResource(resId)
        return input.bufferedReader().readLines()
    }
}
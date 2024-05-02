package com.example.createtogether.ui.fragments.searching

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentDisplayTextFoundBinding
import com.example.createtogether.databinding.FragmentViewChangesTextFoundBinding
import com.example.createtogether.db.models.Request
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.fragments.createcontent.OpenSavedTextsFragmentDirections
import com.example.createtogether.ui.viewmodels.ViewModel
import com.example.createtogether.utility.DiffUtil
import com.example.createtogether.utility.UserUtil
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewChangesTextFoundFragment : Fragment(R.layout.fragment_view_changes_text_found) {

    private lateinit var binding: FragmentViewChangesTextFoundBinding
    private val args: ViewChangesTextFoundFragmentArgs by navArgs()
    private val viewModel: ViewModel by viewModels()

    private lateinit var sharedPreferences: SharedPreferences
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val requestsReference = databaseReference.child("requests")
    private val categoriesReference = databaseReference.child("categories")

    private lateinit var textFound: TextContent
    private lateinit var modifiedTextTitle: String
    private lateinit var modifiedText: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewChangesTextFoundBinding.inflate(inflater,container,false)
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textFound = args.textFound

        val originalTextTitle = textFound.textTitle
        val originalText = textFound.text
        modifiedTextTitle = args.modifiedTextTitle
        modifiedText = args.modifiedText

        val diffUtil = DiffUtil()

        val newTextTitle = diffUtil.generateHighlightedTextWithAdditionsAndRemovals(originalTextTitle, modifiedTextTitle)
        val newText = diffUtil.generateHighlightedTextWithAdditionsAndRemovals(originalText, modifiedText)

        val newTextTitleOnlyAdditions = diffUtil.generateHighlightedTextWithOnlyAdditions(originalTextTitle, modifiedTextTitle)
        val newTextOnlyAdditions = diffUtil.generateHighlightedTextWithOnlyAdditions(originalText, modifiedText)

        binding.tvModifiedTextTitle.text = newTextTitle
        binding.tvModifiedText.text = newText

        binding.switchOnlyShowAdditions.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.tvModifiedTextTitle.text = newTextTitleOnlyAdditions
                binding.tvModifiedText.text = newTextOnlyAdditions
            } else {
                binding.tvModifiedTextTitle.text = newTextTitle
                binding.tvModifiedText.text = newText
            }
        }

        binding.btnSaveLocally.setOnClickListener {

            if(UserUtil.getUserId(requireActivity()) == textFound.creatorId) {
                Toast.makeText(requireActivity(), "You are the creator", Toast.LENGTH_LONG).show()
                saveLocally("Downloaded")
            } else {
                Toast.makeText(requireActivity(), "You are not the creator", Toast.LENGTH_LONG).show()
                saveLocally("Copied")
            }
        }

        binding.btnSendRequest.setOnClickListener {

//            if(textFound.creatorId == UserUtil.getUserId(requireActivity())) {
//                Toast.makeText(requireContext(), "You can't send a request - you are the creator", Toast.LENGTH_LONG).show()
//            } else {
//                val requestId =
//                    requestsReference.push().key ?: ""
//
//                val request = Request(requestId, "Waiting for review", textFound.creator, textFound.textId, modifiedTextTitle, modifiedText)
//                requestsReference.child(textFound.creatorId).child(requestId).setValue(request)
//
//                val action = ViewChangesTextFoundFragmentDirections.actionViewChangesTextFoundFragmentToSearchFragment()
//                findNavController().navigate(action)
//            }

            val requestId =
                requestsReference.push().key ?: ""
            val savedUsername = sharedPreferences.getString("userName", "") ?: "Not found"

            val request = Request(requestId, "Waiting for review", savedUsername, textFound.textId, modifiedTextTitle, modifiedText)
            requestsReference.child(textFound.creatorId).child(requestId).setValue(request)

//            val action = ViewChangesTextFoundFragmentDirections.actionViewChangesTextFoundFragmentToSearchFragment()
//            findNavController().navigate(action)
            requireActivity().finish()
        }

//        binding.btnBack.setOnClickListener {
//            findNavController().popBackStack()
//        }
    }

    private fun saveLocally(status: String) {
        val textId =
            categoriesReference.push().key ?: ""
        //muss noch implementiert werden
        val textToSave = TextContent(
            textFound.creatorId,
            textFound.creator,
            textId,
            textFound.textId,
            modifiedTextTitle,
            modifiedText,
            textFound.category,
            textFound.contributors,
            textFound.likes,
            status
        )

        viewModel.addText(textToSave)

//        val action = ViewChangesTextFoundFragmentDirections.actionViewChangesTextFoundFragmentToSearchFragment()
//        findNavController().navigate(action)
        requireActivity().finish()
    }


}
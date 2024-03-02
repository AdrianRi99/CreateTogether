package com.example.createtogether.ui.requests

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentDisplayTextFoundBinding
import com.example.createtogether.databinding.FragmentViewChangesRequestBinding
import com.example.createtogether.databinding.FragmentViewChangesTextFoundBinding
import com.example.createtogether.databinding.FragmentViewChangesTextSavedBinding
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.viewmodels.ViewModel
import com.example.createtogether.utility.DiffUtil
import com.example.createtogether.utility.UserUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewChangesRequestFragment : Fragment(R.layout.fragment_view_changes_request) {

    private lateinit var binding: FragmentViewChangesRequestBinding
    private val args: ViewChangesRequestFragmentArgs by navArgs()

    private val viewModel: ViewModel by viewModels()

    private lateinit var textContent: TextContent
    private lateinit var modifiedTextTitle: String
    private lateinit var modifiedText: String
    private lateinit var originalTextTitle: String
    private lateinit var originalText: String

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val categoriesReference = databaseReference.child("categories")
    private val requestsReference = databaseReference.child("requests")



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewChangesRequestBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = args.request

        modifiedTextTitle = request.modifiedTextTitle
        modifiedText = request.modifiedText

        viewModel.getTextById(request.textId).observe(requireActivity(), Observer { textContent ->
            if (textContent != null) {
                this.textContent = textContent
                originalTextTitle = this.textContent.textTitle
                originalText = this.textContent.text

                generateLayout()
            }

        })



        val userId = UserUtil.getUserId(requireActivity())

        binding.btnAcceptRequest.setOnClickListener {

            var contributors : String? = textContent.contributors

            if(contributors == null) {
                contributors = request.requestCreator
            } else {
                contributors += ", ${request.requestCreator}"
            }

            Log.d("HeyJa", contributors)

            val likesRef = categoriesReference.child(textContent.category).child(textContent.textId).child("likes")

            var likes: Int
            var updatedTextFromRequest: TextContent
            likesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val likesValue = dataSnapshot.getValue(Int::class.java)
                    Log.d("HeyJa", likesValue.toString())

                    if (likesValue != null) {
                        likes = likesValue

                        updatedTextFromRequest = TextContent(
                            textContent.creatorId,
                            textContent.creator,
                            textContent.textId,
                            modifiedTextTitle,
                            modifiedText,
                            textContent.category,
                            contributors,
                            likes
                        )

                        categoriesReference.child(textContent.category).child(textContent.textId)
                            .setValue(updatedTextFromRequest)
                        viewModel.updateText(updatedTextFromRequest)
                    } else {
                        updatedTextFromRequest = TextContent(
                            textContent.creatorId,
                            textContent.creator,
                            textContent.textId,
                            modifiedTextTitle,
                            modifiedText,
                            textContent.category,
                            contributors,
                            0
                        )

                        categoriesReference.child(textContent.category).child(textContent.textId)
                            .setValue(updatedTextFromRequest)
                        viewModel.updateText(updatedTextFromRequest)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Bei Bedarf kannst du hier mit Fehlern umgehen
                }
            })

            requestsReference.child(userId).child(request.requestId).child("requestStatus").setValue("Accepted")
            requireActivity().finish()
        }

        binding.btnDeclineRequest.setOnClickListener {
            Log.d("HeyJa", "Declined")

            requestsReference.child(userId).child(request.requestId).child("requestStatus").setValue("Declined")
            requireActivity().finish()

        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun generateLayout() {

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
    }


}
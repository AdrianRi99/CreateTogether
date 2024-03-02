package com.example.createtogether.ui.fragments.createcontent

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
import com.example.createtogether.databinding.FragmentDisplayTextFoundBinding
import com.example.createtogether.databinding.FragmentViewChangesTextFoundBinding
import com.example.createtogether.databinding.FragmentViewChangesTextSavedBinding
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.viewmodels.ViewModel
import com.example.createtogether.utility.DiffUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewChangesTextSavedFragment : Fragment(R.layout.fragment_view_changes_text_saved) {

    private lateinit var binding: FragmentViewChangesTextSavedBinding
    private val args: ViewChangesTextSavedFragmentArgs by navArgs()

    private val viewModel: ViewModel by viewModels()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val categoriesReference = databaseReference.child("categories")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewChangesTextSavedBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textSaved = args.textSaved

        val originalTextTitle = textSaved.textTitle
        val originalText = textSaved.text
        val modifiedTextTitle = args.modifiedTextTitle
        val modifiedText = args.modifiedText

        val diffUtil = DiffUtil()

        val newTextTitle = diffUtil.generateHighlightedTextWithAdditionsAndRemovals(
            originalTextTitle,
            modifiedTextTitle
        )
        val newText =
            diffUtil.generateHighlightedTextWithAdditionsAndRemovals(originalText, modifiedText)

        val newTextTitleOnlyAdditions =
            diffUtil.generateHighlightedTextWithOnlyAdditions(originalTextTitle, modifiedTextTitle)
        val newTextOnlyAdditions =
            diffUtil.generateHighlightedTextWithOnlyAdditions(originalText, modifiedText)

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


        binding.btnUpdateText.setOnClickListener {

            val likesRef =
                categoriesReference.child(textSaved.category).child(textSaved.textId).child("likes")

            var likes: Int
            var updatedText: TextContent
            likesRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val likesValue = dataSnapshot.getValue(Int::class.java)
                    Log.d("HeyJa", likesValue.toString())

                    if (likesValue != null) {
                        likes = likesValue
                        updatedText = TextContent(
                            textSaved.creatorId,
                            textSaved.creator,
                            textSaved.textId,
                            modifiedTextTitle,
                            modifiedText,
                            textSaved.category,
                            textSaved.contributors,
                            likes
                        )

                        categoriesReference.child(textSaved.category).child(textSaved.textId)
                            .setValue(updatedText)
                        viewModel.updateText(updatedText)
                    } else {
                        updatedText = TextContent(
                            textSaved.creatorId,
                            textSaved.creator,
                            textSaved.textId,
                            modifiedTextTitle,
                            modifiedText,
                            textSaved.category,
                            textSaved.contributors,
                            0
                        )

                        categoriesReference.child(textSaved.category).child(textSaved.textId)
                            .setValue(updatedText)
                        viewModel.updateText(updatedText)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Bei Bedarf kannst du hier mit Fehlern umgehen
                }
            })

            val action =
                ViewChangesTextSavedFragmentDirections.actionViewChangesTextSavedFragmentToCreateContentFragment()
            findNavController().navigate(action)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
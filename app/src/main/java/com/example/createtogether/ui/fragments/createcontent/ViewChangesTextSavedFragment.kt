package com.example.createtogether.ui.fragments.createcontent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.createtogether.R
import com.example.createtogether.databinding.FragmentViewChangesTextSavedBinding
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.dialogs.UploadVersionDialog
import com.example.createtogether.ui.viewmodels.ViewModel
import com.example.createtogether.utility.DiffUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewChangesTextSavedFragment : Fragment(R.layout.fragment_view_changes_text_saved),
    UploadVersionDialog.UploadVersionDialogListener {

    private lateinit var binding: FragmentViewChangesTextSavedBinding
    private val args: ViewChangesTextSavedFragmentArgs by navArgs()

    private val viewModel: ViewModel by viewModels()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val categoriesReference = databaseReference.child("categories")

    private lateinit var textSaved: TextContent
    private lateinit var modifiedTextTitle: String
    private lateinit var modifiedText: String


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

        textSaved = args.textSaved

        val originalTextTitle = textSaved.textTitle
        val originalText = textSaved.text
        modifiedTextTitle = args.modifiedTextTitle
        modifiedText = args.modifiedText

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

            if (textSaved.status == "Uploaded") {
                updateText()
            } else if (textSaved.status == "Downloaded") {
                val uploadVersionDialog = UploadVersionDialog(this)
                uploadVersionDialog.show(
                    requireActivity().supportFragmentManager,
                    "uploadVersionDialog"
                )
            } else if (textSaved.status == "Copied") {
                Toast.makeText(
                    requireActivity(),
                    "You can't update this text as you are not the creator! " +
                            "If you copy this text and brand it as your own, you can get banned!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun updateText() {
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
                        textSaved.textAuthenticator,
                        modifiedTextTitle,
                        modifiedText,
                        textSaved.category,
                        textSaved.contributors,
                        likes,
                        "Uploaded"
                    )

                    categoriesReference.child(textSaved.category).child(textSaved.textId)
                        .setValue(updatedText)
                    viewModel.updateText(updatedText)
                } else {
                    updatedText = TextContent(
                        textSaved.creatorId,
                        textSaved.creator,
                        textSaved.textId,
                        textSaved.textAuthenticator,
                        modifiedTextTitle,
                        modifiedText,
                        textSaved.category,
                        textSaved.contributors,
                        0,
                        "Uploaded"
                    )

                    categoriesReference.child(textSaved.category).child(textSaved.textId)
                        .setValue(updatedText)
                    viewModel.updateText(updatedText)


                }

                Toast.makeText(
                    requireActivity(),
                    "Text Uploaded",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Bei Bedarf kannst du hier mit Fehlern umgehen
            }
        })

        val action =
            ViewChangesTextSavedFragmentDirections.actionViewChangesTextSavedFragmentToCreateContentFragment()
        findNavController().navigate(action)
    }

    override fun confirm() {
        updateText()
    }

}
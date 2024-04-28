package com.example.createtogether.ui.tempPackage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.createtogether.R
import com.example.createtogether.databinding.ActivityWriteTextBinding
import com.example.createtogether.db.models.TextContent
import com.example.createtogether.ui.viewmodels.ViewModel
import com.example.createtogether.utility.UserUtil
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import kotlin.random.Random

@AndroidEntryPoint
class WriteTextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteTextBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val viewModel: ViewModel by viewModels()

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val categoriesReference = databaseReference.child("categories")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val selectedTextCategory = intent.getStringExtra("SelectedTextCategory") ?: "Undefined"
        val savedUsername = sharedPreferences.getString("userName", "") ?: "Not found"
        val userId = UserUtil.getUserId(this)
        val textId =
            categoriesReference.push().key ?: "" // Generiere eine eindeutige ID für den Text

        val inspirationTerms = getInspirationTerms(this)

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

            finish()
//            val action = NewTextFragmentDirections.actionNewTextFragmentToCreateContentFragment()
//            findNavController().navigate(action)
        }

//        binding.btnBack.setOnClickListener {
//            val action = NewTextFragmentDirections.actionNewTextFragmentToCreateContentFragment()
//            findNavController().navigate(action)
//        }

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
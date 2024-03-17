package com.example.createtogether.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.createtogether.db.models.TextContent

class UploadVersionDialog (private val uploadVersionDialogListener: UploadVersionDialogListener) : AppCompatDialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        builder
            .setView(view)
            .setTitle("Upload Text")
            .setMessage("Are you sure that you want to upload this text - a version of this text was already shared")
            .setNegativeButton("CANCEL", null)
            .setPositiveButton("OK", null)

        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE) as Button
            positiveButton.setOnClickListener {
                uploadVersionDialogListener.confirm()
//                Toast.makeText(requireContext(), "${file.fileTitle} deleted", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }

        dialog.show()

        return dialog
    }


    interface UploadVersionDialogListener {
        fun confirm()
    }
}
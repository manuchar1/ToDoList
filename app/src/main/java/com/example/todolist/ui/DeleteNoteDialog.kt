package com.example.todolist.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.todolist.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteNoteDialog : DialogFragment() {

    private var positiveListener: (() -> Unit)? = null

    fun setPositiveListener(listener: () -> Unit) {
        positiveListener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.delete_note_dialog_title)
            .setMessage(R.string.delete_note_dialog_message)
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton(R.string.delete_note_dialog_positive) { _, _ ->
                positiveListener?.let { click ->
                    click()
                }
            }
            .setNegativeButton(R.string.delete_note_dialog_negative) { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
    }
}
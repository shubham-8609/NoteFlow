package com.codeleg.noteflow.utils

import android.app.AlertDialog
import android.content.Context
import androidx.fragment.app.Fragment

object DialogHelper {

    /**
     * Generic confirmation dialog
     * @param context Context (can be from Activity or Fragment.requireContext())
     * @param title Dialog title
     * @param message Dialog message
     * @param positiveText Text for confirm button (default: "Yes")
     * @param negativeText Text for cancel button (default: "No")
     * @param onConfirm Callback for confirm action
     * @param onCancel Callback for cancel action (optional)
     */
    fun showConfirmDialog(
        context: Context,
        title: String,
        message: String,
        positiveText: String = "Yes",
        negativeText: String = "No",
        onConfirm: (() -> Unit)? = null,
        onCancel: (() -> Unit)? = null
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText) { dialog, _ ->
                onConfirm?.invoke()
                dialog.dismiss()
            }
            .setNegativeButton(negativeText) { dialog, _ ->
                onCancel?.invoke()
                dialog.dismiss()
            }
            .create()
            .show()
    }

    /**
     * Extension helper for Fragments
     */
    fun Fragment.showConfirmDialog(
        title: String,
        message: String,
        positiveText: String = "Yes",
        negativeText: String = "No",
        onConfirm: (() -> Unit)? = null,
        onCancel: (() -> Unit)? = null
    ) {
        showConfirmDialog(requireContext(), title, message, positiveText, negativeText, onConfirm, onCancel)
    }
}

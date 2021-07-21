package dev.putra.libraries.picker.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import dev.putra.libraries.picker.R
import dev.putra.libraries.picker.constant.ImageProvider
import dev.putra.libraries.picker.databinding.DialogChooseAppBinding
import dev.putra.libraries.picker.listener.ResultListener

internal object DialogHelper {

    /**
     * Show Image Provide Picker Dialog. This will streamline the code to pick/capture image
     *
     */
    fun showChooseAppDialog(context: Context, listener: ResultListener<ImageProvider>) {
        val layoutInflater = LayoutInflater.from(context)
        val viewBinding = DialogChooseAppBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(context)
            .setTitle(R.string.picker_title_choose_image_provider)
            .setView(viewBinding.root)
            .setOnCancelListener {
                listener.onResult(null)
            }
            .setNegativeButton(R.string.picker_action_cancel) { _, _ ->
                listener.onResult(null)
            }
            .show()

        // Handle Camera option click
        viewBinding.lytCameraPick.setOnClickListener {
            listener.onResult(ImageProvider.CAMERA)
            dialog.dismiss()
        }

        // Handle Gallery option click
        viewBinding.lytGalleryPick.setOnClickListener {
            listener.onResult(ImageProvider.GALLERY)
            dialog.dismiss()
        }
    }
}

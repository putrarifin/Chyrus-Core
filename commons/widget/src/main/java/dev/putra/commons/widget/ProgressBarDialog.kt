package dev.putra.commons.widget

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import dev.putra.commons.widget.databinding.ViewProgressDialogBinding

/**
 * Created by Chyrus on 3/28/2020.
 */

class ProgressBarDialog(context: Context) : AlertDialog(context, R.style.CustomProgressDialog) {

    lateinit var viewBinding: ViewProgressDialogBinding

    override fun show() {
        show(null)
    }

    fun show(messageRes: String?) {
        super.show()
        viewBinding = ViewProgressDialogBinding.inflate(LayoutInflater.from(context))
        setContentView(viewBinding.root)
        setCanceledOnTouchOutside(false)
        setCancelable(false)

        viewBinding.apply {
            progressBarLoading.visibility = View.VISIBLE
            progressBarMessage.text = messageRes
        }
    }

    fun dismissWithErrorMessage(messageRes: String) {
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        viewBinding.apply {
            progressBarLoading.visibility = View.GONE
            progressBarMessage.text = messageRes
        }
    }

}
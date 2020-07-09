package dev.putra.commons.ui.utils

import android.content.Context
import dev.putra.commons.widget.ProgressBarDialog

/**
 * Created by Chyrus on 3/29/2020.
 */
object ProgressDialog {

    @Volatile
    private var INSTANCE: ProgressBarDialog? = null

    fun getInstance(context: Context): ProgressBarDialog {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDialog(context).also {
                INSTANCE = it
            }
        }
    }

    private fun buildDialog(context: Context): ProgressBarDialog {
        return ProgressBarDialog(context)
    }

}
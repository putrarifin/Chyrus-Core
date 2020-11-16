package dev.putra.commons.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import dev.putra.commons.ui.extension.toast
import dev.putra.commons.ui.utils.ProgressDialog
import dev.putra.commons.utilities.data.ErrorNetwork
import dev.putra.commons.widget.ProgressBarDialog

/**
 * Created by Chyrus on 3/28/2020.
 */
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    protected abstract fun getLayoutBinding(): T

    lateinit var binding: T

    protected abstract fun initialized()
    protected abstract fun handleShouldLogout()

    private val loader by lazy {
        ProgressBarDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getLayoutBinding()
        setContentView(binding.root)
        initialized()
    }

    fun handleError(error: ErrorNetwork) {
        toast(error.message)
        if (error.code == 401) {
            handleShouldLogout()
        }
    }

    fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            loader.show()
        } else {
            loader.dismiss()
        }
    }

}
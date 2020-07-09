package dev.putra.commons.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dev.putra.commons.ui.extension.toast
import dev.putra.commons.ui.utils.ProgressDialog
import dev.putra.commons.utilities.data.ErrorNetwork

/**
 * Created by Chyrus on 3/28/2020.
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected abstract fun getLayoutBinding(): T

    lateinit var binding: T

    protected abstract fun initialized()

    private val loader by lazy {
        ProgressDialog.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = getLayoutBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialized()
    }

    protected fun handleError(error: ErrorNetwork) {
        context?.toast(error.message)
    }

    protected fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            loader.show()
        } else {
            loader.dismiss()
        }
    }

}
package dev.putra.commons.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import dev.putra.commons.utilities.data.ErrorNetwork

/**
 * Created by Chyrus on 3/28/2020.
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected abstract fun getLayoutBinding(): T

    lateinit var binding: T

    protected abstract fun initialized()

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
        activity?.let{
            (it as BaseActivity<*>).handleError(error = error)
        }
    }

    protected fun handleLoading(isLoading: Boolean) {
        activity?.let{
            (it as BaseActivity<*>).handleLoading(isLoading = isLoading)
        }
    }

}
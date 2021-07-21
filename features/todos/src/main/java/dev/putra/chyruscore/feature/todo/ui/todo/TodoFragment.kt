package dev.putra.chyruscore.feature.todo.ui.todo

import androidx.lifecycle.lifecycleScope
import dev.putra.chyruscore.feature.todo.databinding.FragmentTodoBinding
import dev.putra.chyruscore.ui.navigator.MainViewModel
import dev.putra.commons.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TodoFragment : BaseFragment<FragmentTodoBinding>() {

    private val navHostViewModel by sharedViewModel<MainViewModel>()

    override fun getLayoutBinding() = FragmentTodoBinding.inflate(layoutInflater)

    override fun initialized() {
        lifecycleScope.launchWhenStarted {
//            navHostViewModel.setShowBottomNav(isGone = false)
        }
    }
}
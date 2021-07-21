package dev.putra.chyruscore.feature.favorite.ui.favorite

import androidx.lifecycle.lifecycleScope
import dev.putra.chyruscore.feature.favorite.databinding.FragmentFavoriteBinding
import dev.putra.chyruscore.ui.navigator.MainViewModel
import dev.putra.commons.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val navHostViewModel by sharedViewModel<MainViewModel>()

    override fun getLayoutBinding() = FragmentFavoriteBinding.inflate(layoutInflater)

    override fun initialized() {
//        lifecycleScope.launchWhenStarted { navHostViewModel.setShowBottomNav(isGone = false) }
    }
}
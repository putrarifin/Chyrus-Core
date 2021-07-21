package dev.putra.chyruscore.feature.auth.ui.splash

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dev.putra.chyruscore.BuildConfig
import dev.putra.chyruscore.feature.auth.databinding.FragmentSplashBinding
import dev.putra.chyruscore.ui.dynamicfeat.NavigateModuleBottomSheetFragment
import dev.putra.commons.ui.base.BaseFragment
import kotlinx.coroutines.delay

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun getLayoutBinding() = FragmentSplashBinding.inflate(layoutInflater)

    override fun initialized() {
        binding.tvVersion.text = BuildConfig.VERSION_NAME
        lifecycleScope.launchWhenCreated {
            delay(2000)
//            NavigateModuleBottomSheetFragment(
//                direction = SplashFragmentDirections.actionSplashFragmentToNavTodoGraph(),
//                isInstalled = {
//                    findNavController().navigate(
//                        SplashFragmentDirections.actionSplashFragmentToNavTodoGraph()
//                    )
//                }
//            ).show(
//                childFragmentManager, "moduleTodo"
//            )
            findNavController().navigate(
                SplashFragmentDirections.actionSplashFragmentToNavTodoGraph(),
            )
        }
    }

}
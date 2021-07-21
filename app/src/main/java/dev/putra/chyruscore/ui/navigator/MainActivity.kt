package dev.putra.chyruscore.ui.navigator

import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dev.putra.chyruscore.R
import dev.putra.chyruscore.databinding.ActivityMainBinding
import dev.putra.chyruscore.helper.NavManager
import dev.putra.chyruscore.ui.dynamicfeat.InternalDeepLink
import dev.putra.commons.ui.base.BaseActivity
import dev.putra.commons.ui.extension.gone
import dev.putra.commons.ui.extension.navigateSafe
import dev.putra.commons.ui.extension.toast
import dev.putra.commons.ui.extension.visible
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val navController get() = findNavController(R.id.nav_host_fragment)

    private val navManager by inject<NavManager>()

    private val mainViewModel by viewModel<MainViewModel>()

    override fun getLayoutBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initialized() {
        supportActionBar?.hide()
        initFCM()
        initNavManager()
        initBottomNavigation()
        initListenerNav()
        lifecycleScope.launchWhenCreated {
            mainViewModel.isShowBottomNav.collect {
                binding.bottomNav.isGone = it
            }
        }
    }

    private fun initFCM() {
        Firebase.messaging.isAutoInitEnabled = true
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            println("TOKEN: $token")
            // Log and toast
            toast(token.toString())
        })

        Firebase.messaging.subscribeToTopic("utangku")
            .addOnCompleteListener { task ->
                var msg = "SUKSES SUBSKREB"
                if (!task.isSuccessful) {
                    msg = "GAGAL SUBSKREB"
                }
                toast(msg)
            }
    }

    private fun initListenerNav() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                InternalDeepLink.TODO_LABEL, InternalDeepLink.FAVORITE_LABEL -> binding.bottomNav.visible()
                else -> binding.bottomNav.gone()
            }
        }
    }

    override fun handleShouldLogout() {
        //reset data here
    }

    private fun initBottomNavigation() {
        binding.bottomNav.setupWithNavController(navController)
        // Disables reselection of bottom menu item, so fragments are not recreated when clicking on the same menu item
        binding.bottomNav.setOnNavigationItemReselectedListener { }
    }

    private fun initNavManager() {
        navManager.setOnNavEvent {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

            currentFragment?.navigateSafe(it)
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.label) {
            InternalDeepLink.TODO_LABEL, InternalDeepLink.FAVORITE_LABEL -> {
                //do nothing
            }
            else -> super.onBackPressed()
        }
    }
}
package dev.putra.chyruscore.ui.navigator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _isShowBottomNav = MutableStateFlow(true)
    val isShowBottomNav: StateFlow<Boolean> = _isShowBottomNav

    fun setShowBottomNav(isGone: Boolean) {
        _isShowBottomNav.value = isGone
    }

}
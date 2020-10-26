package dev.putra.commons.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.putra.commons.ui.extension.toLiveData
import dev.putra.commons.utilities.data.ErrorNetwork

abstract class BaseViewModel : ViewModel() {

    protected val _loading = MutableLiveData<Boolean>()
    val loading by lazy { _loading.toLiveData() }
    protected val _error = MutableLiveData<ErrorNetwork>()
    val error by lazy { _error.toLiveData() }

}
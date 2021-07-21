package dev.putra.chyruscore.ui.dynamicfeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.dynamicfeatures.fragment.ui.AbstractProgressFragment
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import dev.putra.chyruscore.R
import dev.putra.chyruscore.databinding.FragmentProgressBinding

class InstallModuleProgressFragment : AbstractProgressFragment(R.layout.fragment_progress) {

    private lateinit var binding: FragmentProgressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProgressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onFailed(errorCode: Int) {
        val errorMessage =
            when (errorCode) {
                SplitInstallErrorCode.ACCESS_DENIED -> "ACCESS_DENIED"
                SplitInstallErrorCode.ACTIVE_SESSIONS_LIMIT_EXCEEDED -> "ACTIVE_SESSIONS_LIMIT_EXCEEDED"
                SplitInstallErrorCode.API_NOT_AVAILABLE -> "API_NOT_AVAILABLE"
                SplitInstallErrorCode.APP_NOT_OWNED -> "APP_NOT_OWNED"
                SplitInstallErrorCode.INCOMPATIBLE_WITH_EXISTING_SESSION -> "INCOMPATIBLE_WITH_EXISTING_SESSION"
                SplitInstallErrorCode.INSUFFICIENT_STORAGE -> "INSUFFICIENT_STORAGE"
                SplitInstallErrorCode.INTERNAL_ERROR -> "INTERNAL_ERROR"
                SplitInstallErrorCode.INVALID_REQUEST -> "INVALID_REQUEST"
                SplitInstallErrorCode.MODULE_UNAVAILABLE -> "MODULE_UNAVAILABLE"
                SplitInstallErrorCode.NETWORK_ERROR -> "NETWORK_ERROR"
                SplitInstallErrorCode.NO_ERROR -> "NO_ERROR"
                SplitInstallErrorCode.PLAY_STORE_NOT_FOUND -> "PLAY_STORE_NOT_FOUND"
                SplitInstallErrorCode.SESSION_NOT_FOUND -> "SESSION_NOT_FOUND"
                SplitInstallErrorCode.SPLITCOMPAT_COPY_ERROR -> "SPLITCOMPAT_COPY_ERROR"
                SplitInstallErrorCode.SPLITCOMPAT_EMULATION_ERROR -> "SPLITCOMPAT_EMULATION_ERROR"
                SplitInstallErrorCode.SPLITCOMPAT_VERIFICATION_ERROR -> "SPLITCOMPAT_VERIFICATION_ERROR"
                else -> getString(R.string.installing_module_failed)
            }
        binding.message.text = errorMessage
    }

    override fun onCancelled() {
        binding.message.text = getString(R.string.installing_module_cancelled)
    }

    override fun onProgress(status: Int, bytesDownloaded: Long, bytesTotal: Long) {
        binding.progressBar.progress = (bytesDownloaded.toDouble() * 100 / bytesTotal).toInt()
    }
}
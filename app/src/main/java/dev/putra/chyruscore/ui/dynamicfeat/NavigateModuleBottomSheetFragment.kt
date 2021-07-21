package dev.putra.chyruscore.ui.dynamicfeat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.dynamicfeatures.DynamicExtras
import androidx.navigation.dynamicfeatures.DynamicInstallMonitor
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.play.core.ktx.moduleNames
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import dev.putra.chyruscore.R
import dev.putra.chyruscore.databinding.SheetProgressBinding

class NavigateModuleBottomSheetFragment(
    private val direction: NavDirections,
    private val isInstalled: () -> Unit
) :
    BottomSheetDialogFragment() {

    private lateinit var viewBinding: SheetProgressBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = SheetProgressBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val installMonitor = DynamicInstallMonitor()
        navigateTo(direction, installMonitor)
        if (installMonitor.isInstallRequired) {
            with(viewBinding) {
                progressBar.visibility = View.VISIBLE
                installMonitor.status.observe(
                    viewLifecycleOwner,
                    object : Observer<SplitInstallSessionState> {
                        override fun onChanged(sessionState: SplitInstallSessionState) {
                            when (sessionState.status()) {
                                SplitInstallSessionStatus.DOWNLOADING -> {
                                    viewBinding.message.text =
                                        getString(
                                            R.string.installing_module,
                                            sessionState.moduleNames.firstOrNull() ?: ""
                                        )
                                }
                                SplitInstallSessionStatus.INSTALLED -> {
                                    viewBinding.progressBar.visibility = View.GONE
                                    isInstalled.invoke()
                                }
                                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION ->
                                    requestInfoInstallConfirmation(
                                        sessionState
                                    )
                                SplitInstallSessionStatus.FAILED -> showInfoInstallFailed()
                                SplitInstallSessionStatus.CANCELED -> showInfoInstallCanceled()
                            }
                            viewBinding.progressBar.progress =
                                (sessionState.bytesDownloaded()
                                    .toDouble() * 100 / sessionState.totalBytesToDownload()).toInt()

                            if (sessionState.hasTerminalStatus()) {
                                installMonitor.status.removeObserver(this)
                            }

                        }
                    })
            }
        }
    }

    private fun navigateTo(direction: NavDirections, installMonitor: DynamicInstallMonitor) {
        findNavController().navigate(
            direction,
            DynamicExtras(installMonitor)
        )
    }

    private fun requestInfoInstallConfirmation(sessionState: SplitInstallSessionState) {
        viewBinding.progressBar.visibility = View.GONE
        SplitInstallManagerFactory.create(requireContext()).startConfirmationDialogForResult(
            sessionState,
            requireActivity(),
            INSTALL_REQUEST_CODE
        )
    }

    private fun showInfoInstallFailed() {
        viewBinding.progressBar.visibility = View.GONE
        viewBinding.message.text = getString(R.string.installation_failed)
    }

    private fun showInfoInstallCanceled() {
        viewBinding.progressBar.visibility = View.GONE
        viewBinding.message.text = getString(R.string.installation_cancelled)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == INSTALL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                showInfoInstallCanceled()
            }
        }
    }

    companion object {
        const val INSTALL_REQUEST_CODE = 100
    }

}
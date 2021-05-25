package com.canlioya.technicaltest.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.canlioya.technicaltest.R
import com.canlioya.technicaltest.common.isOnline
import com.canlioya.technicaltest.common.showSnack
import com.canlioya.technicaltest.databinding.FragmentListBinding
import com.canlioya.technicaltest.model.UIState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Base fragment which contains common functionality between
 * [AlbumListFragment] and [PhotoListFragment]
 * As two fragments reuse same layout, they share binding type.
 * They both have an observable [uiState] and they react to uiState changes
 * by showing either the loading animation, the list of items, or the
 * network error message. When there is a network error, they observe
 * network state with a broadcast receiver and unregister broadcast receiver
 * when connection is back or when user quits the app
 *
 * @constructor Create BaseListfragment
 */
abstract class BaseFragment : Fragment(R.layout.fragment_list) {

    /**
     * Binding instance is initialized by the third-party viewBinding delegate property
     * and automatically cleaned, to prevent leaking binding instance in fragments
     */
    protected val binding by viewBinding(FragmentListBinding::bind)

    private var networkReceiver: NetworkReceiver? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            uiState = getViewModel().uiState
            binding.executePendingBindings()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                getViewModel().uiState.collectLatest { state ->
                    /*When there is a network error, it shows no network screen
                and start to listen to network state by registering a broadcast listener*/
                    if (state == UIState.ERROR) {
                        binding.root.showSnack(
                            text = R.string.no_internet_warning,
                            length = Snackbar.LENGTH_INDEFINITE,
                            backgroundColor = R.color.dark_red
                        )
                        startListeningNetworkState()
                    }
                }
            }
        }

    }

    /**
     * Inheritors will provide their implementations of
     * the BaseViewModel
     * @return BaseViewModel
     */
    abstract fun getViewModel() : BaseViewModel

    /**
     * When fetching failed with a network error, we start listening for
     * network state by registering a broadcast receiver for CONNECTIVITY_CHANGE
     */
    private fun startListeningNetworkState() {
        Timber.d("Start listening network state")
        if (networkReceiver == null) {
            networkReceiver = NetworkReceiver()
        }
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        context?.registerReceiver(networkReceiver, intentFilter)
    }

    /**
     * When connection is reestablished or the user quits the app
     * we unregister from the broadcast receiver
     */
    private fun stopListeningNetworkState() {
        Timber.d("Stop listening network state")
        networkReceiver?.let {
            context?.unregisterReceiver(it)
            networkReceiver = null
        }
    }

    /**
     * Broadcast receiver for listening to network state.
     * When triggered, we check if network is available
     * and if available we start fetching again, we show
     * a snack for informing the user and we unregister
     * from the broadcast receiver
     */
    inner class NetworkReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Timber.d("Broadcast received for network state")
            if (isOnline(context)) {
                Timber.d("is online")
                stopListeningNetworkState()
                viewLifecycleOwner.lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        getViewModel().startFetching()
                    }
                }
                binding.root.showSnack(
                    text = R.string.internet_is_back,
                    backgroundColor = R.color.light_blue
                )
            }
        }
    }

    /**
     * If user quits the app while broadcast receiver is active,
     * we unregister not to waste system resources
     */
    override fun onStop() {
        super.onStop()
        if (getViewModel().uiState.value == UIState.ERROR) {
            stopListeningNetworkState()
        }
    }

    /**
     * If the app was in error state when user put the
     * app in the background, when user comes back we
     * continue listening for network state
     */
    override fun onStart() {
        super.onStart()
        if (getViewModel().uiState.value == UIState.ERROR) {
            startListeningNetworkState()
        }
    }


}
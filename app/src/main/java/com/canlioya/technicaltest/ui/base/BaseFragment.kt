package com.canlioya.technicaltest.ui.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.canlioya.technicaltest.R
import com.canlioya.technicaltest.common.isOnline
import com.canlioya.technicaltest.common.showSnack
import com.canlioya.technicaltest.databinding.FragmentListBinding
import com.canlioya.technicaltest.model.UIState
import com.google.android.material.snackbar.Snackbar
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            uiState = getViewModel().uiState
            binding.executePendingBindings()
        }

        getViewModel().uiState.observe(viewLifecycleOwner, {
            /*When there is a network error, it shows no network screen
            and start to listen to network state by registering a broadcast listener*/
            if (it == UIState.ERROR) {
                binding.root.showSnack(
                    text = R.string.no_internet_warning,
                    length = Snackbar.LENGTH_INDEFINITE,
                    backgroundColor = R.color.dark_red
                )
            }
        })
    }

    /**
     * Inheritors will provide their implementations of
     * the BaseViewModel
     * @return BaseViewModel
     */
    abstract fun getViewModel() : BaseViewModel


}
package com.canlioya.technicaltest.ui.albums

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.canlioya.technicaltest.R
import com.canlioya.technicaltest.common.showSnack
import com.canlioya.technicaltest.databinding.FragmentListBinding
import com.canlioya.technicaltest.model.Album
import com.canlioya.technicaltest.model.UIState
import com.canlioya.technicaltest.ui.base.BaseFragment
import com.canlioya.technicaltest.ui.base.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AlbumListFragment : BaseFragment(), AlbumClickListener {

    /**
     * AlbumViewModel is scoped to the activity, so that albums are not
     * fetched again each time we go back and forth between albums and photos fragments
     */
    private val viewModel: AlbumViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = setRecyclerView()

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            uiState = viewModel.uiState
            binding.executePendingBindings()
        }

        //Claim and observe albums from the viewmodel
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.albums.collectLatest {
                    if (it.isNotEmpty()) {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel

    private fun setRecyclerView(): AlbumListAdapter {
        val adapter = AlbumListAdapter(this)

        val dividerItemDecoration = DividerItemDecoration(
            requireActivity(),
            LinearLayoutManager.VERTICAL
        )

        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
            addItemDecoration(dividerItemDecoration)
        }
        return adapter
    }

    /**
     * When an album is clicked, we navigate to the PhotoListFragment
     * and we pass the chosenAlbum. albumId will be needed for fetching
     * photos of that album and albumTitle will be displayed on the action bar
     */
    override fun onAlbumClicked(chosenAlbum: Album) {
        val action =
            AlbumListFragmentDirections.actionAlbumListFragmentToPhotoListFragment(chosenAlbum)
        binding.root.findNavController().navigate(action)
    }
}

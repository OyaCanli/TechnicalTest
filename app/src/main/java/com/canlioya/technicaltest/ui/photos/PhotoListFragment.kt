package com.canlioya.technicaltest.ui.photos

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.canlioya.technicaltest.R
import com.canlioya.technicaltest.model.Album
import com.canlioya.technicaltest.ui.base.BaseFragment
import com.canlioya.technicaltest.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class PhotoListFragment : BaseFragment() {

    private val viewModel: PhotoViewModel by viewModels()

    private lateinit var chosenAlbum: Album

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chosenAlbum = PhotoListFragmentArgs.fromBundle(requireArguments()).chosenAlbum
        Timber.d("album Id: ${chosenAlbum.albumId} album name : ${chosenAlbum.albumTitle}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.title = chosenAlbum.albumTitle

        val adapter = setRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.photos.collectLatest {
                    if (it.isNotEmpty()) {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun setRecyclerView(): PhotoAdapter {
        val adapter = PhotoAdapter()

        val spanCount = context?.resources?.getInteger(R.integer.column_count) ?: 3

        binding.list.apply {
            layoutManager = GridLayoutManager(context, spanCount)
            this.adapter = adapter
        }
        return adapter
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
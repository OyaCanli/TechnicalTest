package com.canlioya.technicaltest.ui.photos

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.canlioya.technicaltest.model.Album
import com.canlioya.technicaltest.ui.base.BaseFragment
import com.canlioya.technicaltest.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
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

        viewModel.photos.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
            }
        })
    }

    private fun setRecyclerView(): PhotoAdapter {
        val adapter = PhotoAdapter()

        binding.list.apply {
            layoutManager = GridLayoutManager(context, 3) //todo: span count
            this.adapter = adapter
        }
        return adapter
    }

    override fun getViewModel(): BaseViewModel = viewModel
}
package com.canlioya.technicaltest.ui.albums

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.canlioya.technicaltest.R
import com.canlioya.technicaltest.model.Album
import com.canlioya.technicaltest.ui.base.ItemViewHolder


class AlbumListAdapter(private val clickListener : AlbumClickListener) : ListAdapter<Album, ItemViewHolder<Album>>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<Album> =
        ItemViewHolder.from(parent, R.layout.item_album)

    override fun onBindViewHolder(holder: ItemViewHolder<Album>, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
        holder.binding.root.setOnClickListener {
            clickListener.onAlbumClicked(currentItem)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem == newItem
        }
    }
}

interface AlbumClickListener {
    fun onAlbumClicked(chosenAlbum : Album)
}


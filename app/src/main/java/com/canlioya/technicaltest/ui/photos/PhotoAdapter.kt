package com.canlioya.technicaltest.ui.photos

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.canlioya.technicaltest.R
import com.canlioya.technicaltest.common.NetworkIdlingResource
import com.canlioya.technicaltest.model.Album
import com.canlioya.technicaltest.model.Photo
import com.canlioya.technicaltest.ui.base.ItemViewHolder

class PhotoAdapter: ListAdapter<Photo, ItemViewHolder<Photo>>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<Photo> =
        ItemViewHolder.from(parent, R.layout.item_photo)

    override fun onBindViewHolder(holder: ItemViewHolder<Photo>, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    override fun submitList(list: List<Photo>?) {
        NetworkIdlingResource.increment()
        val commitCallback = Runnable {
            NetworkIdlingResource.decrement()
        }
        super.submitList(list, commitCallback)
    }

    class DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
}

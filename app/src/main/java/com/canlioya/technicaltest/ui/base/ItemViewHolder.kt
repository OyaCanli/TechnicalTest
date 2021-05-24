package com.canlioya.technicaltest.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.canlioya.technicaltest.BR

/**
 * Generic item view holder which works with data binding
 * and can be reused as long as item layout has a variable
 * called "item" of any type.
 * Click listener is not included in the default implementation
 * and should be added in the onBindViewHolder callback
 * of the adapter if needed
 */
class ItemViewHolder<T> (val binding: ViewDataBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(currentItem: T) {
        binding.setVariable(BR.item, currentItem)
        binding.executePendingBindings()
    }

    companion object {
        fun <T> from(parent: ViewGroup, @LayoutRes layoutId: Int): ItemViewHolder<T> {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater, layoutId, parent, false
            )
            return ItemViewHolder(binding)
        }
    }
}
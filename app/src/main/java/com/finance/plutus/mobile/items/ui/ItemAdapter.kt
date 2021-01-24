package com.finance.plutus.mobile.items.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.databinding.ItemViewBinding
import com.finance.plutus.mobile.items.data.model.Item

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class ItemAdapter(
    private val swipeListener: ItemSwipeListener
) : PagingDataAdapter<Item, ItemAdapter.ItemViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ItemViewHolder(
        private val binding: ItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(item: Item?) {
            item?.let {
                binding.itemNameTv.text = item.name
            }
        }
    }

    fun onEdit(position: Int) {
        getItem(position)?.let { swipeListener.edit(it) }
    }

    fun onDelete(position: Int) {
        getItem(position)?.let { swipeListener.delete(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemViewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_view,
            parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.render(getItem(position))
    }

}
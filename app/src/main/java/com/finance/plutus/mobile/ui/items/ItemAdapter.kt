package com.finance.plutus.mobile.ui.items

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.Item
import com.finance.plutus.mobile.databinding.ItemViewBinding
import com.finance.plutus.mobile.ext.AdapterExtensions.ITEM_BOTTOM
import com.finance.plutus.mobile.ext.AdapterExtensions.ITEM_MIDDLE
import com.finance.plutus.mobile.ext.AdapterExtensions.ITEM_TOP
import com.finance.plutus.mobile.ext.AdapterExtensions.setupLayout

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class ItemAdapter(
    private val swipeListener: ItemSwipeListener,
    private val context: Context
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

        fun render(item: Item?, itemType: Int) {
            item?.let {
                setupLayout(itemType, context, binding.itemCard)
                binding.itemNameTv.text = item.name
            }
        }
    }

    fun onEdit(position: Int) {
        getItem(position)?.let { swipeListener.edit(it) }
    }

    fun onDelete(position: Int) {
        getItem(position)?.let {
            swipeListener.delete(it)
        }
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
        holder.render(getItem(position), getItemViewType(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ITEM_TOP
            itemCount - 1 -> ITEM_BOTTOM
            else -> ITEM_MIDDLE
        }
    }

}
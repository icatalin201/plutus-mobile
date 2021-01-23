package com.finance.plutus.mobile.view.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.databinding.ItemViewBinding
import com.finance.plutus.mobile.model.Item

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class ItemAdapter(
    private val listener: ItemClickListener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val items: MutableList<Item> = mutableListOf()

    inner class ItemViewHolder(
        private val binding: ItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(item: Item) {
            binding.itemNameTv.text = item.name
        }

    }

    fun submit(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun onEdit(position: Int) {
        listener.edit(items[position])
    }

    fun onDelete(position: Int) {
        listener.delete(items[position])
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
        holder.render(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
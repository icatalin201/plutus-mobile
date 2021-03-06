package com.finance.plutus.mobile.ui.partners

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.Partner
import com.finance.plutus.mobile.databinding.PartnerViewBinding
import com.finance.plutus.mobile.ext.AdapterExtensions
import com.finance.plutus.mobile.ext.AdapterExtensions.setupLayout

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
class PartnerAdapter(
    private val swipeListener: PartnerSwipeListener,
    private val context: Context
) : PagingDataAdapter<Partner, PartnerAdapter.PartnerViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Partner>() {
            override fun areItemsTheSame(oldItem: Partner, newItem: Partner): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Partner, newItem: Partner): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class PartnerViewHolder(
        private val binding: PartnerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(partner: Partner?, itemType: Int) {
            partner?.let {
                setupLayout(itemType, context, binding.partnerLayout)
                binding.partnerNameTv.text = partner.name
                binding.partnerCountryTv.text = partner.country.name
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: PartnerViewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.partner_view,
            parent, false
        )
        return PartnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PartnerViewHolder, position: Int) {
        holder.render(getItem(position), getItemViewType(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && itemCount == 1) {
            AdapterExtensions.ITEM_SINGLE
        } else if (position == 0) {
            AdapterExtensions.ITEM_TOP
        } else if (position == itemCount - 1) {
            AdapterExtensions.ITEM_BOTTOM
        } else {
            AdapterExtensions.ITEM_MIDDLE
        }
    }

}
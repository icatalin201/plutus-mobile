package com.finance.plutus.mobile.partners.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.databinding.PartnerViewBinding
import com.finance.plutus.mobile.partners.data.model.Partner

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
class PartnerAdapter(
    private val swipeListener: PartnerSwipeListener
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

        fun render(partner: Partner?) {
            partner?.let {
                binding.partnerNameTv.text = partner.name
                binding.partnerCountryTv.text = partner.country.name
            }
        }

    }

    fun onEdit(position: Int) {
        getItem(position)?.let { swipeListener.edit(it) }
    }

    fun onDelete(position: Int) {
        getItem(position)?.let { swipeListener.delete(it) }
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
        holder.render(getItem(position))
    }

}
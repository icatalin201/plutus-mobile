package com.finance.plutus.mobile.view.partners

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.databinding.PartnerViewBinding
import com.finance.plutus.mobile.model.Partner

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
class PartnerAdapter(
    private val listener: PartnerClickListener
) : RecyclerView.Adapter<PartnerAdapter.PartnerViewHolder>() {

    private val partners: MutableList<Partner> = mutableListOf()

    inner class PartnerViewHolder(
        private val binding: PartnerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(partner: Partner) {
            binding.partnerNameTv.text = partner.name
            binding.partnerCountryTv.text = partner.country.name
        }

    }

    fun submit(partners: List<Partner>) {
        this.partners.clear()
        this.partners.addAll(partners)
        notifyDataSetChanged()
    }

    fun onEdit(position: Int) {
        listener.edit(partners[position])
    }

    fun onDelete(position: Int) {
        listener.delete(partners[position])
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
        holder.render(partners[position])
    }

    override fun getItemCount(): Int {
        return partners.size
    }

}
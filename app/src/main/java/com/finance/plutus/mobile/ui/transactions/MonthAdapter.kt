package com.finance.plutus.mobile.ui.transactions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.TransactionMonth
import com.finance.plutus.mobile.databinding.MonthViewBinding

/**
 * Plutus Finance
 * Created by Catalin on 1/31/2021
 **/
class MonthAdapter(
    private val months: List<TransactionMonth>,
    private val context: Context,
    private val listener: MonthClickListener
) : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {

    inner class MonthViewHolder(
        private val binding: MonthViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(month: TransactionMonth) {
            binding.monthCard.setOnClickListener {
                select(month)
            }
            binding.monthTitleText.text = month.title
            if (month.selected) {
                binding.monthCard.setCardBackgroundColor(context.getColor(R.color.surface))
                binding.monthTitleText.setTextColor(context.getColor(R.color.colorPrimaryText))
            } else {
                binding.monthCard.setCardBackgroundColor(context.getColor(R.color.background))
                binding.monthTitleText.setTextColor(context.getColor(R.color.colorSecondaryText))
            }
        }
    }

    fun select(month: TransactionMonth) {
        resetLastSelected()
        month.selected = true
        notifyItemChanged(months.indexOf(month))
        listener.onClick(month)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: MonthViewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.month_view,
            parent, false
        )
        return MonthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder.render(months[position])
    }

    override fun getItemCount(): Int {
        return months.size
    }

    private fun resetLastSelected() {
        val lastSelected = months.find { month -> month.selected }
        lastSelected?.let {
            lastSelected.selected = false
            val lastIndex = months.indexOf(lastSelected)
            notifyItemChanged(lastIndex)
        }
    }

}
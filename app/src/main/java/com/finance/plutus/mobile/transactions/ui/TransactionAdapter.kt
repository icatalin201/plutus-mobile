package com.finance.plutus.mobile.transactions.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.AdapterExtensions
import com.finance.plutus.mobile.app.util.AdapterExtensions.setupLayout
import com.finance.plutus.mobile.app.util.formatInLocalCurrency
import com.finance.plutus.mobile.databinding.TransactionViewBinding
import com.finance.plutus.mobile.transactions.data.model.Transaction
import com.finance.plutus.mobile.transactions.data.model.TransactionType

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
class TransactionAdapter(
    private val swipeListener: TransactionSwipeListener,
    private val listener: TransactionClickListener,
    private val context: Context
) : PagingDataAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class TransactionViewHolder(
        private val binding: TransactionViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(transaction: Transaction?, viewType: Int) {
            transaction?.let {
                binding.transactionCard.setOnClickListener { listener.onClick(transaction) }
                setupLayout(viewType, context, binding.transactionCard)
                binding.transactionNameTv.text = transaction.document
                binding.transactionDateTv.text = transaction.date
                binding.transactionValueTv.text = transaction.value.formatInLocalCurrency()
                val color = when (transaction.type) {
                    TransactionType.INCOME -> android.R.color.holo_green_dark
                    TransactionType.EXPENSE -> android.R.color.holo_red_dark
                }
                binding.transactionValueTv.setTextColor(context.getColor(color))
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

    fun onCashing(position: Int) {
        getItem(position)?.let {
            swipeListener.collect(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: TransactionViewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.transaction_view,
            parent, false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.render(getItem(position), getItemViewType(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> AdapterExtensions.ITEM_TOP
            itemCount - 1 -> AdapterExtensions.ITEM_BOTTOM
            else -> AdapterExtensions.ITEM_MIDDLE
        }
    }
}
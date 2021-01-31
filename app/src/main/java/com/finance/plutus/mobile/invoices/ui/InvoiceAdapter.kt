package com.finance.plutus.mobile.invoices.ui

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
import com.finance.plutus.mobile.databinding.InvoiceViewBinding
import com.finance.plutus.mobile.invoices.data.model.Invoice
import com.finance.plutus.mobile.invoices.data.model.InvoiceStatus

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
class InvoiceAdapter(
    private val swipeListener: InvoiceSwipeListener,
    private val context: Context
) : PagingDataAdapter<Invoice, InvoiceAdapter.InvoiceViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Invoice>() {
            override fun areItemsTheSame(oldItem: Invoice, newItem: Invoice): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Invoice, newItem: Invoice): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class InvoiceViewHolder(
        private val binding: InvoiceViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(invoice: Invoice?, viewType: Int) {
            invoice?.let {
                setupLayout(viewType, context, binding.invoiceCard)
                binding.invoiceNameTv.text = invoice.name
                binding.invoiceDateTv.text = invoice.date
                binding.invoiceValueTv.text = invoice.total.formatInLocalCurrency()
            }
        }
    }

    fun onEdit(position: Int) {
        getItem(position)?.let { swipeListener.edit(it) }
    }

    fun onDelete(position: Int) {
        getItem(position)?.let {
            swipeListener.delete(it)
            notifyItemRemoved(position)
        }
    }

    fun onCashing(position: Int) {
        getItem(position)?.let {
            swipeListener.collect(it)
        }
    }

    fun isDraft(position: Int): Boolean {
        return getItem(position)?.status == InvoiceStatus.DRAFT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: InvoiceViewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.invoice_view,
            parent, false
        )
        return InvoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
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
package com.finance.plutus.mobile.ui.updateinvoice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.network.payload.InvoiceLineUpdateRequest
import com.finance.plutus.mobile.databinding.EditableInvoiceLineViewBinding

/**
Plutus Finance
Created by Catalin on 1/25/2021
 **/
class EditableInvoiceLineAdapter(
    private val listener: EditableInvoiceLineListener
) : RecyclerView.Adapter<EditableInvoiceLineAdapter.EditableInvoiceLineViewHolder>() {

    private val invoiceLines = mutableListOf<InvoiceLineUpdateRequest>()

    inner class EditableInvoiceLineViewHolder(
        private val binding: EditableInvoiceLineViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(invoiceLine: InvoiceLineUpdateRequest) {
            binding.request = invoiceLine
            binding.invoiceLineItem.setOnClickListener {
                listener.onInvoiceItemClick(invoiceLine) { item ->
                    binding.invoiceLineItem.setText(item.name)
                }
            }
        }

    }

    fun submit(invoiceLines: List<InvoiceLineUpdateRequest>) {
        this.invoiceLines.clear()
        this.invoiceLines.addAll(invoiceLines)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditableInvoiceLineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: EditableInvoiceLineViewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.editable_invoice_line_view,
            parent, false
        )
        return EditableInvoiceLineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EditableInvoiceLineViewHolder, position: Int) {
        holder.render(invoiceLines[position])
    }

    override fun getItemCount(): Int {
        return invoiceLines.size
    }

}
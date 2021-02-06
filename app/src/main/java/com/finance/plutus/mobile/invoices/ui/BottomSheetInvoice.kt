package com.finance.plutus.mobile.invoices.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.formatInLocalCurrency
import com.finance.plutus.mobile.app.util.formatInUsCurrency
import com.finance.plutus.mobile.databinding.InvoiceBottomSheetBinding
import com.finance.plutus.mobile.invoices.data.model.Invoice
import com.finance.plutus.mobile.transactions.ui.BottomSheetTransaction
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
Plutus Finance
Created by Catalin on 2/6/2021
 **/
class BottomSheetInvoice : BottomSheetDialogFragment() {

    companion object {

        private const val INVOICE = "Invoice"

        fun getInstance(invoice: Invoice): BottomSheetInvoice {
            val bottomSheet = BottomSheetInvoice()
            val bundle = Bundle()
            bundle.putParcelable(INVOICE, invoice)
            bottomSheet.arguments = bundle
            return bottomSheet
        }
    }

    private lateinit var binding: InvoiceBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.invoice_bottom_sheet,
            container,
            false
        )
        val invoice = arguments?.getParcelable<Invoice>(INVOICE)
        invoice?.let {
            binding.invoice = invoice
            binding.invoiceValueTv.text = invoice.total.formatInLocalCurrency()
            invoice.currency?.let { currency ->
                binding.invoiceCurrencyValueTv.text = currency.total.formatInUsCurrency()
                binding.invoiceCurrencyValueTv.isVisible = true
            }
            if (invoice.lines.isNotEmpty()) {
                val line = invoice.lines[0]
                binding.invoiceItemNameTv.text = line.item.name
                binding.invoiceItemPriceTv.text = line.total.formatInLocalCurrency()
            }
        }
        return binding.root
    }

}

package com.finance.plutus.mobile.ui.updateinvoice

import com.finance.plutus.mobile.data.model.Item
import com.finance.plutus.mobile.data.network.payload.InvoiceLineUpdateRequest

/**
Plutus Finance
Created by Catalin on 1/25/2021
 **/
interface EditableInvoiceLineListener {
    fun onInvoiceItemClick(invoiceLine: InvoiceLineUpdateRequest, callback: (Item) -> Unit)
}
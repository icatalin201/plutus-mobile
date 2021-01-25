package com.finance.plutus.mobile.invoices.ui

import com.finance.plutus.mobile.app.network.payload.InvoiceLineUpdateRequest
import com.finance.plutus.mobile.items.data.model.Item

/**
Plutus Finance
Created by Catalin on 1/25/2021
 **/
interface EditableInvoiceLineListener {
    fun onInvoiceItemClick(invoiceLine: InvoiceLineUpdateRequest, callback: (Item) -> Unit)
}
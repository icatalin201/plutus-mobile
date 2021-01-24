package com.finance.plutus.mobile.invoices.ui

import com.finance.plutus.mobile.invoices.data.model.Invoice

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
interface InvoiceSwipeListener {
    fun delete(invoice: Invoice)
    fun edit(invoice: Invoice)
}
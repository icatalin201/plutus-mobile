package com.finance.plutus.mobile.ui.invoices

import com.finance.plutus.mobile.data.model.Invoice

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
interface InvoiceSwipeListener {
    fun delete(invoice: Invoice)
    fun edit(invoice: Invoice)
    fun collect(invoice: Invoice)
}
package com.finance.plutus.mobile.ui.invoices

import com.finance.plutus.mobile.data.model.Invoice

/**
Plutus Finance
Created by Catalin on 2/6/2021
 **/
interface InvoiceClickListener {
    fun onClick(invoice: Invoice)
}
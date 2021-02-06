package com.finance.plutus.mobile.invoices.ui

import com.finance.plutus.mobile.invoices.data.model.Invoice

/**
Plutus Finance
Created by Catalin on 2/6/2021
 **/
interface InvoiceClickListener {
    fun onClick(invoice: Invoice)
}
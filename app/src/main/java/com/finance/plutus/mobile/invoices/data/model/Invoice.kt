package com.finance.plutus.mobile.invoices.data.model

import com.finance.plutus.mobile.partners.data.model.Partner
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Invoice(
    val id: UUID,
    val name: String,
    val partner: Partner,
    val date: String,
    val dueDate: String,
    val subtotal: Double,
    val taxes: Double,
    val total: Double,
    val status: InvoiceStatus,
    val currency: InvoiceCurrency?,
    val lines: List<InvoiceLine>,
)

package com.finance.plutus.mobile.invoices.data.model

import com.finance.plutus.mobile.items.data.model.Item
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class InvoiceLine(
    val id: UUID,
    val item: Item,
    val uom: String?,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double,
    val vat: Double,
    val total: Double,
    val currency: InvoiceCurrency?,
)

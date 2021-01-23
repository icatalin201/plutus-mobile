package com.finance.plutus.mobile.model

import java.time.LocalDateTime
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class InvoiceLine(
    val id: UUID,
    val createdOn: LocalDateTime,
    val updatedOn: LocalDateTime,
    val item: Item,
    val uom: String?,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double,
    val vat: Double,
    val total: Double,
    val currency: InvoiceCurrency?,
)

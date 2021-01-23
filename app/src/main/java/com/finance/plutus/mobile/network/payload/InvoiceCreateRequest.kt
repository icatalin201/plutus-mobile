package com.finance.plutus.mobile.network.payload

import com.finance.plutus.mobile.model.Currency
import java.time.LocalDate
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class InvoiceCreateRequest(
    val partnerId: UUID,
    val serialId: UUID,
    val currency: Currency,
    val date: LocalDate,
    val dueDate: LocalDate?,
    val lines: List<InvoiceLineCreateRequest>,
) {

    data class InvoiceLineCreateRequest(
        val itemId: UUID,
        val quantity: Int,
        val vat: Double,
        val unitPrice: Double,
        val uom: String?,
        val details: String?,
    )

}

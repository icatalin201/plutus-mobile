package com.finance.plutus.mobile.model

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class InvoiceCurrency(
    val currency: Currency,
    val rate: Double,
    val subtotal: Double,
    val total: Double,
)

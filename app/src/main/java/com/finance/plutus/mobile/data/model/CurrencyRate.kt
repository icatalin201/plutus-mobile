package com.finance.plutus.mobile.data.model

/**
Plutus Finance
Created by Catalin on 1/25/2021
 **/
data class CurrencyRate(
    val date: String,
    val currency: Currency,
    val rate: Double
)

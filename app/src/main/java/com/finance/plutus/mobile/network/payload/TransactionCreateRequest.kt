package com.finance.plutus.mobile.network.payload

import com.finance.plutus.mobile.model.Currency
import com.finance.plutus.mobile.model.TransactionMethod
import com.finance.plutus.mobile.model.TransactionType
import java.time.LocalDate
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class TransactionCreateRequest(
    val date: LocalDate,
    val document: String,
    val details: String,
    val partnerId: UUID,
    val value: Double,
    val type: TransactionType,
    val method: TransactionMethod,
    val deductible: Boolean,
    val currency: Currency?,
    val currencyValue: Double?,
)

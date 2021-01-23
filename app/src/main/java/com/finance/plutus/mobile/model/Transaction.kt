package com.finance.plutus.mobile.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Transaction(
    val id: UUID,
    val date: LocalDate,
    val document: String,
    val details: String,
    val partner: Partner,
    val type: TransactionType,
    val method: TransactionMethod,
    val value: Double,
    val createdOn: LocalDateTime,
    val updatedOn: LocalDateTime,
    val status: TransactionStatus,
    val deductible: Boolean,
)

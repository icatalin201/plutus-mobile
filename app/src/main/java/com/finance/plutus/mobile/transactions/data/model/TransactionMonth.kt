package com.finance.plutus.mobile.transactions.data.model

import java.time.LocalDate

/**
 * Plutus Finance
 * Created by Catalin on 1/31/2021
 **/
data class TransactionMonth(
    val title: String,
    val date: LocalDate,
    var selected: Boolean
)

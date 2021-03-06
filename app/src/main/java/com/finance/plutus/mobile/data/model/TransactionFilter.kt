package com.finance.plutus.mobile.data.model

import java.time.LocalDate
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
data class TransactionFilter(
    var partnerId: UUID? = null,
    var type: TransactionType? = null,
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null,
    var deductible: Boolean? = null
)

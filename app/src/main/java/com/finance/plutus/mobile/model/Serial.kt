package com.finance.plutus.mobile.model

import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Serial(
    val id: UUID,
    val name: String,
    val startNumber: Int,
    val currentNumber: Int,
    val nextNumber: Int,
)
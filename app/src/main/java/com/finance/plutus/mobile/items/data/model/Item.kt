package com.finance.plutus.mobile.items.data.model

import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Item(
    val id: UUID,
    val name: String,
    val unitPrice: Double,
    val totalPrice: Double,
    val vat: Double,
    val type: ItemType,
    val uom: String?,
    val code: String?,
    val description: String?,
)

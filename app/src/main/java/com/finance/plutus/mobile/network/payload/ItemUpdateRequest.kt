package com.finance.plutus.mobile.network.payload

import com.finance.plutus.mobile.model.ItemType

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class ItemUpdateRequest(
    val name: String,
    val unitPrice: Double,
    val vat: Double,
    val type: ItemType,
    val uom: String?,
    val code: String?,
    val description: String?
)

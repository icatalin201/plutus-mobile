package com.finance.plutus.mobile.model

import java.time.LocalDateTime
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Partner(
    val id: UUID,
    val name: String,
    val email: String?,
    val type: PartnerType,
    val phone: String?,
    val vat: String?,
    val commercialRegistry: String?,
    val address: String?,
    val termInDays: Int?,
    val bankAccount: String?,
    val bank: Bank?,
    val country: Country,
    val businessType: BusinessType,
    val createdOn: LocalDateTime,
    val updatedOn: LocalDateTime
)

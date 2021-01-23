package com.finance.plutus.mobile.network.payload

import com.finance.plutus.mobile.model.BusinessType
import com.finance.plutus.mobile.model.PartnerType
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class PartnerUpdateRequest(
    val name: String,
    val email: String?,
    val type: PartnerType,
    val phone: String?,
    val vat: String?,
    val commercialRegistry: String?,
    val address: String?,
    val termInDays: Int?,
    val bankAccount: String?,
    val bankId: UUID?,
    val countryCode: String,
    val businessType: BusinessType,
)

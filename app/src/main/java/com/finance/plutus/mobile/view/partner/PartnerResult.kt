package com.finance.plutus.mobile.view.partner

import androidx.annotation.StringRes

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
data class PartnerResult(
    val ok: Boolean? = null,
    @StringRes
    val error: Int? = null
)
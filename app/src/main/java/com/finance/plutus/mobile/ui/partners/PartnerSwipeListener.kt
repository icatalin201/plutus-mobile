package com.finance.plutus.mobile.ui.partners

import com.finance.plutus.mobile.data.model.Partner

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
interface PartnerSwipeListener {
    fun delete(partner: Partner)
    fun edit(partner: Partner)
}
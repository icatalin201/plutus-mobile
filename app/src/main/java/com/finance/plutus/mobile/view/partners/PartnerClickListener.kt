package com.finance.plutus.mobile.view.partners

import com.finance.plutus.mobile.model.Item
import com.finance.plutus.mobile.model.Partner

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
interface PartnerClickListener {
    fun delete(partner: Partner)
    fun edit(partner: Partner)
}
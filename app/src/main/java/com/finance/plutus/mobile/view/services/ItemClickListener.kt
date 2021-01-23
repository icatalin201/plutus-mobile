package com.finance.plutus.mobile.view.services

import com.finance.plutus.mobile.model.Item

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
interface ItemClickListener {
    fun delete(item: Item)
    fun edit(item: Item)
}
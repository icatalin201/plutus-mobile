package com.finance.plutus.mobile.items.ui

import com.finance.plutus.mobile.items.data.model.Item

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
interface ItemSwipeListener {
    fun delete(item: Item)
    fun edit(item: Item)
}
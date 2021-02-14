package com.finance.plutus.mobile.ui.items

import com.finance.plutus.mobile.data.model.Item

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
interface ItemSwipeListener {
    fun delete(item: Item)
    fun edit(item: Item)
}
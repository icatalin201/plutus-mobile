package com.finance.plutus.mobile.ui.transactions

import com.finance.plutus.mobile.data.model.Transaction

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
interface TransactionSwipeListener {
    fun edit(transaction: Transaction)
    fun delete(transaction: Transaction)
    fun collect(transaction: Transaction)
}
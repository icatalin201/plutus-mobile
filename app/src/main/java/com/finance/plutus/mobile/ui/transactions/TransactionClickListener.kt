package com.finance.plutus.mobile.ui.transactions

import com.finance.plutus.mobile.data.model.Transaction

/**
 * Plutus Finance
 * Created by Catalin on 2/6/2021
 */
interface TransactionClickListener {
    fun onClick(transaction: Transaction)
}
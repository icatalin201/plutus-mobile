package com.finance.plutus.mobile.ui.transactions

import com.finance.plutus.mobile.data.model.TransactionMonth

/**
 * Plutus Finance
 * Created by Catalin on 1/31/2021
 **/
interface MonthClickListener {
    fun onClick(month: TransactionMonth)
}
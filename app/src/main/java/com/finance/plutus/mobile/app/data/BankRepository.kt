package com.finance.plutus.mobile.app.data

import com.finance.plutus.mobile.app.data.model.Bank
import io.reactivex.Single

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface BankRepository {
    fun findAll(): Single<List<Bank>>
}
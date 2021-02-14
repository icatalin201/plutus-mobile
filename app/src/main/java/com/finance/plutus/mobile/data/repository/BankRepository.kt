package com.finance.plutus.mobile.data.repository

import com.finance.plutus.mobile.data.model.Bank
import io.reactivex.Single

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface BankRepository {
    fun findAll(): Single<List<Bank>>
}
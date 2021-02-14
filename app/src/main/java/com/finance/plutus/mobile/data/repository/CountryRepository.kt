package com.finance.plutus.mobile.data.repository

import com.finance.plutus.mobile.data.model.Country
import io.reactivex.Single

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface CountryRepository {
    fun findAll(): Single<List<Country>>
}
package com.finance.plutus.mobile.app.data

import com.finance.plutus.mobile.app.data.model.Country
import io.reactivex.Single

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface CountryRepository {
    fun findAll(): Single<List<Country>>
}
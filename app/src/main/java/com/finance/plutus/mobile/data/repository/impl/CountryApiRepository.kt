package com.finance.plutus.mobile.data.repository.impl

import com.finance.plutus.mobile.data.model.Country
import com.finance.plutus.mobile.data.network.PlutusService
import com.finance.plutus.mobile.data.repository.CountryRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class CountryApiRepository(
    private val plutusService: PlutusService
) : CountryRepository {
    override fun findAll(): Single<List<Country>> {
        return plutusService.findAllCountries()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }
    }
}
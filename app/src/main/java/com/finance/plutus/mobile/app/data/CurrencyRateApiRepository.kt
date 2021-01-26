package com.finance.plutus.mobile.app.data

import com.finance.plutus.mobile.app.data.model.CurrencyRate
import com.finance.plutus.mobile.app.network.PlutusService
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
Plutus Finance
Created by Catalin on 1/25/2021
 **/
class CurrencyRateApiRepository(
    private val plutusService: PlutusService
) : CurrencyRateRepository {

    override fun fetchTodayRates(): Flowable<List<CurrencyRate>> {
        return plutusService.fetchTodayRates()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }.toFlowable()
    }
}
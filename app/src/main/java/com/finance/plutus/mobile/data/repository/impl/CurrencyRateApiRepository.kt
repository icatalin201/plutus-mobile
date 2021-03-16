package com.finance.plutus.mobile.data.repository.impl

import com.finance.plutus.mobile.data.model.CurrencyRate
import com.finance.plutus.mobile.data.network.PlutusService
import com.finance.plutus.mobile.data.repository.CurrencyRateRepository
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
                .subscribeOn(Schedulers.io()).toFlowable()
    }
}
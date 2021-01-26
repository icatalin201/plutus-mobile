package com.finance.plutus.mobile.app.data

import com.finance.plutus.mobile.app.data.model.CurrencyRate
import io.reactivex.Flowable

/**
 * Plutus Finance
 * Created by Catalin on 1/25/2021
 */
interface CurrencyRateRepository {
    fun fetchTodayRates(): Flowable<List<CurrencyRate>>
}
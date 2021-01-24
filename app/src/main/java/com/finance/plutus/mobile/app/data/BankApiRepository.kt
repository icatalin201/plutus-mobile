package com.finance.plutus.mobile.app.data

import com.finance.plutus.mobile.app.data.model.Bank
import com.finance.plutus.mobile.app.network.PlutusService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class BankApiRepository(
    private val plutusService: PlutusService
) : BankRepository {

    override fun findAll(): Single<List<Bank>> {
        return plutusService.findAllBanks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }
    }
}
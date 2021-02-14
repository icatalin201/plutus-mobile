package com.finance.plutus.mobile.data.repository.impl

import com.finance.plutus.mobile.data.model.Bank
import com.finance.plutus.mobile.data.network.PlutusService
import com.finance.plutus.mobile.data.repository.BankRepository
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
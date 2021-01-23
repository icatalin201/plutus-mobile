package com.finance.plutus.mobile.repository.impl

import com.finance.plutus.mobile.model.Business
import com.finance.plutus.mobile.network.PlutusService
import com.finance.plutus.mobile.repository.UserRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 */
class UserApiRepository(
    private val plutusService: PlutusService
) : UserRepository {
    override fun getBusiness(): Single<Business> {
        return plutusService.getBusiness()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }
    }
}
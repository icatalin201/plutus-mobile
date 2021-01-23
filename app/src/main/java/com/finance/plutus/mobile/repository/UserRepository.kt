package com.finance.plutus.mobile.repository

import com.finance.plutus.mobile.model.Business
import io.reactivex.Single

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface UserRepository {
    fun getBusiness(): Single<Business>
}
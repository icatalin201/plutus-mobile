package com.finance.plutus.mobile.login.data

import com.finance.plutus.mobile.app.network.payload.AuthorizationResponse
import io.reactivex.Completable
import io.reactivex.Single

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface TokenRepository {
    fun generateToken(username: String, password: String): Single<AuthorizationResponse>
    fun refreshToken(): Completable
    fun saveToken(token: String, validity: Int)
    fun saveRefreshToken(refreshToken: String)
    fun getToken(): String
    fun getRefreshToken(): String
}
package com.finance.plutus.mobile.login.data

import com.finance.plutus.mobile.BuildConfig
import com.finance.plutus.mobile.app.network.payload.AuthorizationResponse
import com.finance.plutus.mobile.app.network.LoginService
import com.finance.plutus.mobile.app.util.SharedPreferencesUtil
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class TokenRepositoryImpl(
    private val sharedPreferencesUtil: SharedPreferencesUtil,
    private val loginService: LoginService
) : TokenRepository {

    companion object {
        private const val TOKEN_VALUE_KEY = "PLUTUS.Token.Value"
        private const val REFRESH_TOKEN_VALUE_KEY = "PLUTUS.Refresh.Token.Value"
        private const val TOKEN_VALIDITY_KEY = "PLUTUS.Token.Validity"
    }

    override fun generateToken(
        username: String,
        password: String
    ): Single<AuthorizationResponse> {
        return loginService.login(username, password, "password", BuildConfig.CLIENT_ID)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun refreshToken(): Completable {
        return Completable.fromRunnable {
            val refreshToken = getRefreshToken()
            val response = loginService
                .refreshToken(refreshToken, "refresh_token", BuildConfig.CLIENT_ID)
                .blockingGet()
            saveToken(response.accessToken, response.expiresIn)
            saveRefreshToken(response.refreshToken)
        }
    }

    override fun saveToken(token: String, validity: Int) {
        sharedPreferencesUtil.save(TOKEN_VALUE_KEY, token)
        val validUntil = LocalDateTime.now(ZoneOffset.UTC)
            .plusSeconds(validity.toLong())
        sharedPreferencesUtil.save(
            TOKEN_VALIDITY_KEY, validUntil
                .toEpochSecond(ZoneOffset.UTC)
        )
    }

    override fun saveRefreshToken(refreshToken: String) {
        sharedPreferencesUtil.save(REFRESH_TOKEN_VALUE_KEY, refreshToken)
    }

    override fun getToken(): String {
        return sharedPreferencesUtil
            .get(TOKEN_VALUE_KEY) ?: "token"
    }

    override fun getRefreshToken(): String {
        return sharedPreferencesUtil
            .get(REFRESH_TOKEN_VALUE_KEY) ?: "token"
    }
}
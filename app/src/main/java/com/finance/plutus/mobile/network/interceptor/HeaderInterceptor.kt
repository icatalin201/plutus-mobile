package com.finance.plutus.mobile.network.interceptor

import com.finance.plutus.mobile.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class HeaderInterceptor(
    private val tokenRepository: TokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenRepository.getToken()
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/vnd.plutus.finance+json")
            .addHeader(
                "Authorization",
                "Bearer $token"
            ).build()
        return chain.proceed(request)
    }

}
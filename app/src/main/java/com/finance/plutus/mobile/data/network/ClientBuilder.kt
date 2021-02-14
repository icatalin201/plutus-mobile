package com.finance.plutus.mobile.data.network

import com.finance.plutus.mobile.BuildConfig
import com.finance.plutus.mobile.data.network.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
object ClientBuilder {

    fun createApiClient(headerInterceptor: HeaderInterceptor): Retrofit {
        return build(BuildConfig.PLUTUS_API_URL, headerInterceptor)
    }

    fun createLoginClient(): Retrofit {
        return build(BuildConfig.AUTH_API_URL)
    }

    private fun build(
        url: String,
        headerInterceptor: HeaderInterceptor? = null
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClientBuilder = OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
        headerInterceptor?.let { okHttpClientBuilder.addInterceptor(it) }
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}
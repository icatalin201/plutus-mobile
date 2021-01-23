package com.finance.plutus.mobile.network

import com.finance.plutus.mobile.network.payload.AuthorizationResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 */
interface LoginService {

    @FormUrlEncoded
    @POST("auth/realms/plutus/protocol/openid-connect/token")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
    ): Single<AuthorizationResponse>

    @FormUrlEncoded
    @POST("auth/realms/plutus/protocol/openid-connect/token")
    fun refreshToken(
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
    ): Single<AuthorizationResponse>
}
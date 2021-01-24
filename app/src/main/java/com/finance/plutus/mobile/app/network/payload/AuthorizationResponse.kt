package com.finance.plutus.mobile.app.network.payload

import com.google.gson.annotations.SerializedName

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class AuthorizationResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("refresh_expires_in")
    val refreshExpiresIn: Int,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("not_before_policy")
    val notBeforePolicy: Int,
    @SerializedName("session_state")
    val sessionState: String,
    val scope: String
)
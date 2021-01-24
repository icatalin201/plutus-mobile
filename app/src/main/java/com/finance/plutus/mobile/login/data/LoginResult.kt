package com.finance.plutus.mobile.login.data

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: Boolean? = null,
    val error: Int? = null
)
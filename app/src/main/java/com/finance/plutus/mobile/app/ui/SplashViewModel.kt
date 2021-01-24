package com.finance.plutus.mobile.app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.login.data.TokenRepository

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class SplashViewModel(
    private val tokenRepository: TokenRepository
) : BaseViewModel() {

    private val _result = MutableLiveData<SplashResult>()
    val result: LiveData<SplashResult> = _result

    init {
        val token = tokenRepository.getToken()
        val result = token != "token" || token.isNotBlank()
        _result.value = SplashResult(result)
    }

}
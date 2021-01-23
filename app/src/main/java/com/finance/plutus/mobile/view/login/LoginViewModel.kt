package com.finance.plutus.mobile.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.repository.TokenRepository

class LoginViewModel(
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        val disposable = tokenRepository.generateToken(username, password)
            .subscribe(
                { response ->
                    tokenRepository.saveToken(response.accessToken, response.expiresIn)
                    tokenRepository.saveRefreshToken(response.refreshToken)
                    _loginResult.value = LoginResult(success = true)
                },
                { error ->
                    error.printStackTrace()
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            )
    }
}
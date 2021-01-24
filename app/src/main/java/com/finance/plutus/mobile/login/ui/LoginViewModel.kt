package com.finance.plutus.mobile.login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.login.data.LoginResult
import com.finance.plutus.mobile.login.data.TokenRepository
import com.finance.plutus.mobile.app.ui.BaseViewModel

class LoginViewModel(
    private val tokenRepository: TokenRepository
) : BaseViewModel() {

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
        compositeDisposable.add(disposable)
    }
}
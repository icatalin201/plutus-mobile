package com.finance.plutus.mobile.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.Result
import com.finance.plutus.mobile.data.repository.TokenRepository
import com.finance.plutus.mobile.ui.BaseViewModel

class LoginViewModel(
    private val tokenRepository: TokenRepository
) : BaseViewModel() {

    private val _loginResult = MutableLiveData<Result>()
    val loginResult: LiveData<Result> = _loginResult

    init {
        if (tokenRepository.isValid()) {
            _loginResult.value = Result(ok = true)
        }
    }

    fun login(username: String, password: String) {
        val disposable = tokenRepository.generateToken(username, password)
            .subscribe(
                { response ->
                    tokenRepository.saveToken(response.accessToken, response.expiresIn)
                    tokenRepository.saveRefreshToken(response.refreshToken)
                    _loginResult.value = Result(ok = true)
                },
                { error ->
                    error.printStackTrace()
                    _loginResult.value = Result(error = R.string.login_failed)
                }
            )
        compositeDisposable.add(disposable)
    }
}
package com.finance.plutus.mobile.ui

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
open class BaseViewModel : ViewModel() {

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}
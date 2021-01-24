package com.finance.plutus.mobile.app.network.payload

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.finance.plutus.mobile.BR
import com.finance.plutus.mobile.transactions.data.model.TransactionMethod
import com.finance.plutus.mobile.transactions.data.model.TransactionType
import java.time.LocalDate
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class TransactionUpdateRequest : BaseObservable() {

    @get:Bindable
    var date: String = LocalDate.now().toString()
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var document: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.document)
        }

    @get:Bindable
    var details: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.details)
        }

    @get:Bindable
    var partnerId: UUID? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.partnerId)
        }

    @get:Bindable
    var value: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.value)
        }

    @get:Bindable
    var type: TransactionType = TransactionType.INCOME
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }

    @get:Bindable
    var method: TransactionMethod = TransactionMethod.BANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.method)
        }

    @get:Bindable
    var deductible: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.deductible)
        }

}

package com.finance.plutus.mobile.app.network.payload

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.finance.plutus.mobile.BR
import com.finance.plutus.mobile.app.data.model.Currency
import java.time.LocalDate
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class InvoiceUpdateRequest : BaseObservable() {

    @get:Bindable
    var partnerId: UUID? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.partnerId)
        }

    @get:Bindable
    var serialId: UUID = UUID.fromString("2e978bc3-115d-4226-90a7-24bd24ef5054")

    @get:Bindable
    var currency: Currency = Currency.USD
        set(value) {
            field = value
            notifyPropertyChanged(BR.currency)
        }

    @get:Bindable
    var date: String = LocalDate.now().toString()
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var dueDate: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.dueDate)
        }

    @get:Bindable
    var lines: List<InvoiceLineUpdateRequest> = mutableListOf()

}

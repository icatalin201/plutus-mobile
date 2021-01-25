package com.finance.plutus.mobile.app.network.payload

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.finance.plutus.mobile.BR
import java.util.*

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
class InvoiceLineUpdateRequest : BaseObservable() {

    @get:Bindable
    var itemId: UUID? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.itemId)
        }

    @get:Bindable
    var quantity: Int = 1
        set(value) {
            field = value
            notifyPropertyChanged(BR.quantity)
        }

    @get:Bindable
    var vat: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.vat)
        }

    @get:Bindable
    var unitPrice: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.unitPrice)
        }

    @get:Bindable
    var uom: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.uom)
        }

    @get:Bindable
    var details: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.details)
        }
}
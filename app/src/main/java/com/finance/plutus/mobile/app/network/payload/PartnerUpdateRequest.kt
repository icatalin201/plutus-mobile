package com.finance.plutus.mobile.app.network.payload

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.finance.plutus.mobile.BR
import com.finance.plutus.mobile.app.data.model.BusinessType
import com.finance.plutus.mobile.partners.data.model.PartnerType
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class PartnerUpdateRequest : BaseObservable() {

    @get:Bindable
    var name: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var email: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var type: PartnerType = PartnerType.CLIENT
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var phone: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var vat: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var commercialRegistry: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var address: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var termInDays: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var bankAccount: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var bankId: UUID? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var countryCode: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var businessType: BusinessType = BusinessType.INDIVIDUAL
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

}

package com.finance.plutus.mobile.data.network.payload

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.finance.plutus.mobile.BR
import com.finance.plutus.mobile.data.model.BusinessType
import com.finance.plutus.mobile.data.model.Partner
import com.finance.plutus.mobile.data.model.PartnerType
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

    fun sync(partner: Partner?) {
        partner?.let {
            address = it.address
            bankAccount = it.bankAccount
            bankId = it.bank?.id
            commercialRegistry = it.commercialRegistry
            termInDays = it.termInDays ?: 0
            email = it.email
            name = it.name
            phone = it.phone
            vat = it.vat
            businessType = it.businessType
            countryCode = it.country.code
            type = it.type
        }
    }

}

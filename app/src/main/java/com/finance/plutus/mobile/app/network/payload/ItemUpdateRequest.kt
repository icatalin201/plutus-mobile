package com.finance.plutus.mobile.app.network.payload

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.finance.plutus.mobile.BR
import com.finance.plutus.mobile.items.data.model.Item
import com.finance.plutus.mobile.items.data.model.ItemType

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class ItemUpdateRequest : BaseObservable() {

    @get:Bindable
    var name: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var unitPrice: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.unitPrice)
        }

    @get:Bindable
    var vat: Double = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.vat)
        }

    @get:Bindable
    var type: ItemType = ItemType.SERVICE
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }

    @get:Bindable
    var uom: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.uom)
        }

    @get:Bindable
    var code: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.code)
        }

    @get:Bindable
    var description: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.description)
        }

    fun sync(item: Item?) {
        item?.let {
            name = item.name
            code = item.code
            description = item.description
            type = item.type
            unitPrice = item.unitPrice
            uom = item.uom
            vat = item.vat
        }
    }
}

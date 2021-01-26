package com.finance.plutus.mobile.invoices.data.model

import android.os.Parcel
import android.os.Parcelable
import com.finance.plutus.mobile.app.data.model.Currency

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class InvoiceCurrency(
    val currency: Currency,
    val rate: Double,
    val subtotal: Double,
    val total: Double,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        Currency.valueOf(parcel.readString()!!),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(currency.name)
        parcel.writeDouble(rate)
        parcel.writeDouble(subtotal)
        parcel.writeDouble(total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InvoiceCurrency> {
        override fun createFromParcel(parcel: Parcel): InvoiceCurrency {
            return InvoiceCurrency(parcel)
        }

        override fun newArray(size: Int): Array<InvoiceCurrency?> {
            return arrayOfNulls(size)
        }
    }

}

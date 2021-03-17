package com.finance.plutus.mobile.data.model

import android.os.Parcel
import android.os.Parcelable

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class InvoiceCurrency(
    val value: Currency,
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
        parcel.writeString(value.name)
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

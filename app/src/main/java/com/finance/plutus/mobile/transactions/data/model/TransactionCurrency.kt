package com.finance.plutus.mobile.transactions.data.model

import android.os.Parcel
import android.os.Parcelable
import com.finance.plutus.mobile.app.data.model.Currency

/**
Plutus Finance
Created by Catalin on 1/26/2021
 **/
data class TransactionCurrency(
    val currency: Currency,
    val value: Double
) : Parcelable {

    constructor(parcel: Parcel) : this(
        Currency.valueOf(parcel.readString()!!),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(currency.name)
        parcel.writeDouble(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TransactionCurrency> {
        override fun createFromParcel(parcel: Parcel): TransactionCurrency {
            return TransactionCurrency(parcel)
        }

        override fun newArray(size: Int): Array<TransactionCurrency?> {
            return arrayOfNulls(size)
        }
    }

}

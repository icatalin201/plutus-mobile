package com.finance.plutus.mobile.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Transaction(
    val id: UUID,
    val date: String,
    val document: String,
    val details: String,
    val partner: Partner,
    val type: TransactionType,
    val method: TransactionMethod,
    val value: Double,
    val status: TransactionStatus,
    val deductible: Boolean,
    val currency: TransactionCurrency?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        UUID.fromString(parcel.readString()),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(Partner::class.java.classLoader)!!,
        TransactionType.valueOf(parcel.readString()!!),
        TransactionMethod.valueOf(parcel.readString()!!),
        parcel.readDouble(),
        TransactionStatus.valueOf(parcel.readString()!!),
        parcel.readByte() != 0.toByte(),
        parcel.readParcelable(TransactionCurrency::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id.toString())
        parcel.writeString(date)
        parcel.writeString(document)
        parcel.writeString(details)
        parcel.writeParcelable(partner, flags)
        parcel.writeString(type.name)
        parcel.writeString(method.name)
        parcel.writeDouble(value)
        parcel.writeString(status.name)
        parcel.writeByte(if (deductible) 1 else 0)
        parcel.writeParcelable(currency, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }

}

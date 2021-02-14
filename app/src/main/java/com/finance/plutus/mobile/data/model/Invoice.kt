package com.finance.plutus.mobile.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Invoice(
    val id: UUID,
    val name: String,
    val partner: Partner,
    val date: String,
    val dueDate: String?,
    val subtotal: Double,
    val taxes: Double,
    val total: Double,
    val status: InvoiceStatus,
    val currency: InvoiceCurrency?,
    val lines: List<InvoiceLine>,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        UUID.fromString(parcel.readString()),
        parcel.readString()!!,
        parcel.readParcelable(Partner::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        InvoiceStatus.valueOf(parcel.readString()!!),
        parcel.readParcelable(InvoiceCurrency::class.java.classLoader),
        parcel.createTypedArrayList(InvoiceLine)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id.toString())
        parcel.writeString(name)
        parcel.writeParcelable(partner, flags)
        parcel.writeString(date)
        parcel.writeString(dueDate)
        parcel.writeDouble(subtotal)
        parcel.writeDouble(taxes)
        parcel.writeDouble(total)
        parcel.writeString(status.name)
        parcel.writeParcelable(currency, flags)
        parcel.writeTypedList(lines)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Invoice> {
        override fun createFromParcel(parcel: Parcel): Invoice {
            return Invoice(parcel)
        }

        override fun newArray(size: Int): Array<Invoice?> {
            return arrayOfNulls(size)
        }
    }

}

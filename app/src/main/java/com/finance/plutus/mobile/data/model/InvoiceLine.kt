package com.finance.plutus.mobile.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class InvoiceLine(
    val id: UUID,
    val item: Item,
    val uom: String?,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double,
    val vat: Double,
    val total: Double,
    val currency: InvoiceCurrency?,
    val details: String?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        UUID.fromString(parcel.readString()),
        parcel.readParcelable(Item::class.java.classLoader)!!,
        parcel.readString(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readParcelable(InvoiceCurrency::class.java.classLoader),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id.toString())
        parcel.writeParcelable(item, flags)
        parcel.writeString(uom)
        parcel.writeInt(quantity)
        parcel.writeDouble(unitPrice)
        parcel.writeDouble(subtotal)
        parcel.writeDouble(vat)
        parcel.writeDouble(total)
        parcel.writeParcelable(currency, flags)
        parcel.writeString(details)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InvoiceLine> {
        override fun createFromParcel(parcel: Parcel): InvoiceLine {
            return InvoiceLine(parcel)
        }

        override fun newArray(size: Int): Array<InvoiceLine?> {
            return arrayOfNulls(size)
        }
    }

}

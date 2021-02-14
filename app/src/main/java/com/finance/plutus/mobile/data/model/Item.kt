package com.finance.plutus.mobile.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Item(
    val id: UUID,
    val name: String,
    val unitPrice: Double,
    val totalPrice: Double,
    val vat: Double,
    val type: ItemType,
    val uom: String?,
    val code: String?,
    val description: String?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        UUID.fromString(parcel.readString()),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        ItemType.valueOf(parcel.readString()!!),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id.toString())
        parcel.writeString(name)
        parcel.writeDouble(unitPrice)
        parcel.writeDouble(totalPrice)
        parcel.writeDouble(vat)
        parcel.writeString(type.name)
        parcel.writeString(uom)
        parcel.writeString(code)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }

}

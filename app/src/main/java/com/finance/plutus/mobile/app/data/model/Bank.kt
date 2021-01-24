package com.finance.plutus.mobile.app.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Bank(
    val id: UUID,
    val name: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        UUID.fromString(parcel.readString()),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id.toString())
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Bank> {
        override fun createFromParcel(parcel: Parcel): Bank {
            return Bank(parcel)
        }

        override fun newArray(size: Int): Array<Bank?> {
            return arrayOfNulls(size)
        }
    }

}

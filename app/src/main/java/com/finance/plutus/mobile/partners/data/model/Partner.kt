package com.finance.plutus.mobile.partners.data.model

import android.os.Parcel
import android.os.Parcelable
import com.finance.plutus.mobile.app.data.model.Bank
import com.finance.plutus.mobile.app.data.model.BusinessType
import com.finance.plutus.mobile.app.data.model.Country
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Partner(
    val id: UUID,
    val name: String,
    val email: String?,
    val type: PartnerType,
    val phone: String?,
    val vat: String?,
    val commercialRegistry: String?,
    val address: String?,
    val termInDays: Int?,
    val bankAccount: String?,
    val bank: Bank?,
    val country: Country,
    val businessType: BusinessType,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        UUID.fromString(parcel.readString()),
        parcel.readString()!!,
        parcel.readString(),
        PartnerType.valueOf(parcel.readString()!!),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readParcelable(Bank::class.java.classLoader),
        parcel.readParcelable(Country::class.java.classLoader)!!,
        BusinessType.valueOf(parcel.readString()!!)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id.toString())
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(type.name)
        parcel.writeString(phone)
        parcel.writeString(vat)
        parcel.writeString(commercialRegistry)
        parcel.writeString(address)
        parcel.writeValue(termInDays)
        parcel.writeString(bankAccount)
        parcel.writeParcelable(bank, flags)
        parcel.writeParcelable(country, flags)
        parcel.writeString(businessType.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Partner> {
        override fun createFromParcel(parcel: Parcel): Partner {
            return Partner(parcel)
        }

        override fun newArray(size: Int): Array<Partner?> {
            return arrayOfNulls(size)
        }
    }


}

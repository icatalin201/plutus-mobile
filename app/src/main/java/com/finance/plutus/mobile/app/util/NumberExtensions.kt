package com.finance.plutus.mobile.app.util

import java.text.NumberFormat
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
fun Double.formatInLocalCurrency(): String {
    val romanianLocale = Locale("ro", "RO")
    val formatter = NumberFormat.getCurrencyInstance(romanianLocale)
    return formatter.format(this)
}
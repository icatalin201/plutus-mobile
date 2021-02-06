package com.finance.plutus.mobile.app.util

import java.text.NumberFormat
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
fun Double.formatInLocalCurrency(): String {
    return formatInCurrency("ro", "RO")
}

fun Double.formatInUsCurrency(): String {
    return formatInCurrency("en", "US")
}

fun Double.formatInCurrency(language: String, country: String): String {
    val romanianLocale = Locale(language, country)
    val formatter = NumberFormat.getCurrencyInstance(romanianLocale)
    return formatter.format(this)
}
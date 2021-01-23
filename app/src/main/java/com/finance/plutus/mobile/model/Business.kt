package com.finance.plutus.mobile.model

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
data class Business(
    val name: String,
    val vat: String,
    val commercialRegistry: String,
    val address: String,
    val bankAccount: String,
    val bank: Bank,
    val email: String,
    val phone: String,
    val website: String,
    val vies: String,
)

package com.finance.plutus.mobile.util

import android.app.Application
import android.content.Context

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class SharedPreferencesUtil(
    context: Application
) {

    companion object {
        private const val PLUTUS = "PLUTUS-pref"
    }

    private val sharedPreferences = context.getSharedPreferences(PLUTUS, Context.MODE_PRIVATE)

    fun save(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun save(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun get(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun save(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun get(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun save(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun get(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

}
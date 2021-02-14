package com.finance.plutus.mobile

import android.app.Application
import com.finance.plutus.mobile.util.InjectionModule
import com.finance.plutus.mobile.util.NotificationManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class PlutusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PlutusApplication)
            modules(InjectionModule.appModule)
        }
        NotificationManager.createNotificationChannel(applicationContext)
    }

}
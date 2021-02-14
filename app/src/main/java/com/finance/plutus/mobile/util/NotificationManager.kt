package com.finance.plutus.mobile.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import com.finance.plutus.mobile.R
import java.security.SecureRandom

/**
 * Plutus Finance
 * Created by Catalin on 1/30/2021
 **/
object NotificationManager {

    private const val NOTIFICATION_CHANNEL_ID = "plutus_finance_channel_01"
    private const val CHANNEL_NAME = "Plutus Finance"
    private const val CHANNEL_DESCRIPTION = "Plutus Finance - Notification Channel"

    @JvmStatic
    fun createNotificationChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, CHANNEL_NAME, importance)
        channel.description = CHANNEL_DESCRIPTION
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)
    }

    @JvmStatic
    @JvmOverloads
    fun createNotification(
        context: Context,
        @StringRes text: Int,
        id: Int = SecureRandom().nextInt()
    ) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getText(text))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .build()
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
    }

}
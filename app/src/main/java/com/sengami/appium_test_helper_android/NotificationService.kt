package com.sengami.appium_test_helper_android

import android.annotation.TargetApi
import android.app.*
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.os.Build
import android.os.IBinder

class NotificationService : Service() {

    @TargetApi(26)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent
            ?.getBooleanExtra(Consts.EXTRA_BOOLEAN_STOP_COMMAND, false)
            ?.takeIf { it }
            ?.let { stopSelf() }

        val duration = intent
            ?.getLongExtra(Consts.EXTRA_LONG_VIBRATION_DURATION, 0L)
            ?: 0L

        val pendingIntent = Intent(this, MainActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .let { PendingIntent.getActivity(this, 0, it, FLAG_IMMUTABLE) }

        val notificationChannelId = duration.toString()
        val notificationChannel = NotificationChannel(
            notificationChannelId,
            notificationChannelId,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.vibrationPattern = longArrayOf(0, duration)

        (applicationContext.getSystemService(NotificationManager::class.java) as NotificationManager)
            .createNotificationChannel(notificationChannel)

        val notification = Notification.Builder(this, notificationChannel.id)
            .setContentTitle(applicationContext.getString(R.string.vibration_notification, duration))
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .build()

        startForeground(1, notification)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}
package com.sengami.appium_test_helper_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

/**
 * Will vibrate the device for 2 seconds when the command below is used through ADB
 * adb shell am broadcast -a com.sengami.appium_test_helper_android.vibration --el duration 2000 -n com.sengami.appium_test_helper_android/.VibrationBroadcastReceiver
 */
class VibrationBroadcastReceiver : BroadcastReceiver() {

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context, intent: Intent) = context
        .run { getSystemService(VIBRATOR_SERVICE) as Vibrator }
        .run {
            val duration = intent.getLongExtra(Consts.VIBRATION_DURATION_KEY, 1000)
            if (Build.VERSION.SDK_INT >= 26) {
                val serviceIntent = Intent(context, NotificationService::class.java)
                serviceIntent.putExtra(Consts.EXTRA_LONG_VIBRATION_DURATION, duration)
                context.startForegroundService(serviceIntent)
                Unit
            } else {
                vibrate(duration)
            }
        }
}
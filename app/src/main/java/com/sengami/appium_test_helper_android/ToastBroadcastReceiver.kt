package com.sengami.appium_test_helper_android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Will show a toast with text "This is a sample text" when the command below is used through ADB
 * adb shell am broadcast -a com.sengami.appium_test_helper_android.toast --es message "This\ is\ a\ sample\ text" -n com.sengami.appium_test_helper_android/.ToastBroadcastReceiver
 */
class ToastBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) = Toast.makeText(
        context,
        intent.getStringExtra(Consts.TOAST_MESSAGE_KEY),
        Toast.LENGTH_LONG
    ).show()
}

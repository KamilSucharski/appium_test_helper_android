package com.sengami.appium_test_helper_android

import android.app.Application
import android.content.SharedPreferences

class App : Application() {

    companion object {
        var sharedPreferences: SharedPreferences? = null
    }

    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(
            Consts.SHARED_PREFERENCES_NAME,
            MODE_PRIVATE
        )
    }
}
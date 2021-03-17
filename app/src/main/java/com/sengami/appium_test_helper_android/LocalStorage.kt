package com.sengami.appium_test_helper_android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class LocalStorage(private val sharedPreferences: SharedPreferences) {

    companion object {
        fun fromContext(context: Context): LocalStorage {
            return LocalStorage(context.getSharedPreferences(
                Consts.SHARED_PREFERENCES_NAME,
                Activity.MODE_PRIVATE
            ))
        }
    }

    fun isTestModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(
            Consts.KEY_TEST_MODE_ENABLED,
            false
        )
    }

    @SuppressLint("ApplySharedPref")
    fun setTestModeEnabled(value: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(Consts.KEY_TEST_MODE_ENABLED, value)
            .commit()
    }
}
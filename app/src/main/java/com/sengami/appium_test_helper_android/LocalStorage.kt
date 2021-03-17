package com.sengami.appium_test_helper_android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

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
        Log.d("LocalStorage", "Setting test mode to $value")
        sharedPreferences
            .edit()
            .putBoolean(Consts.KEY_TEST_MODE_ENABLED, value)
            .commit()
        Log.d("LocalStorage", "Test mode set")
    }

    fun getPackageNames(): List<String> {
        return sharedPreferences
            .getStringSet(Consts.KEY_PACKAGE_NAMES, emptySet())
            ?.toList()
            ?: emptyList()
    }

    @SuppressLint("ApplySharedPref")
    fun setPackageNames(packageNames: List<String>) {
        Log.d("LocalStorage", "Setting package names to $packageNames")
        sharedPreferences
            .edit()
            .putStringSet(Consts.KEY_PACKAGE_NAMES, packageNames.toSet())
            .commit()
        Log.d("LocalStorage", "Package names set")
    }
}
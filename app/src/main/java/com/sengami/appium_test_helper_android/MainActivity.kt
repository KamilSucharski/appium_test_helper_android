package com.sengami.appium_test_helper_android

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.sengami.appium_test_helper_android.databinding.ActivityMainBinding
import java.util.*

class MainActivity : Activity() {

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkForNotificationsPermission()
        }
    }

    override fun onResume() {
        super.onResume()
        grantPackageVisibilityToAllApps()
    }

    private fun initializeView() {
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

        val localStorage = LocalStorage.fromContext(this)
        binding.testModeSwitch.isChecked = localStorage.isTestModeEnabled()
        binding.testModeContainer.setOnClickListener {
            val newState = !localStorage.isTestModeEnabled()
            localStorage.setTestModeEnabled(newState)
            binding.testModeSwitch.isChecked = newState
        }
    }

    @TargetApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkForNotificationsPermission() {
        requestPermissions(arrayOf("android.permission.POST_NOTIFICATIONS"), 0)
    }

    private fun grantPackageVisibilityToAllApps() {
        val uri = Uri.parse(Consts.CONTENT_PROVIDER_URI)
        val packages = when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            true -> getInstalledPackages33()
            false -> getInstalledPackagesLegacy()
        }
        packages.forEach {
            grantUriPermission(
                it.packageName,
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }
    }

    @TargetApi(Build.VERSION_CODES.TIRAMISU)
    private fun getInstalledPackages33(): List<PackageInfo> {
        return packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(0))
    }

    @SuppressWarnings("deprecation")
    private fun getInstalledPackagesLegacy(): List<PackageInfo> {
        return packageManager.getInstalledPackages(0)
    }

}
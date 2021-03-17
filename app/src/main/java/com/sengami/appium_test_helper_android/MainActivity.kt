package com.sengami.appium_test_helper_android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sengami.appium_test_helper_android.databinding.ActivityMainBinding
import java.util.*

class MainActivity : Activity() {

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onResume() {
        super.onResume()
        val uri = Uri.parse(Consts.CONTENT_PROVIDER_URI)
        packageManager
            .getInstalledPackages(0)
            .forEach {
                grantUriPermission(
                    it.packageName,
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
    }
}

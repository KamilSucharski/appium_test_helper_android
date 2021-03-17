package com.sengami.appium_test_helper_android

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.sengami.appium_test_helper_android.databinding.ActivityMainBinding

class MainActivity : Activity() {

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )

        contentResolver
            .getType(Uri.parse(Consts.CONTENT_PROVIDER_URI))
            .takeIf { it == null }
            ?.let {
                Toast.makeText(
                    this,
                    R.string.error_content_provider,
                    Toast.LENGTH_SHORT
                ).show()
            }

        val localStorage = LocalStorage.fromContext(this)
        binding.testModeSwitch.isChecked = localStorage.isTestModeEnabled()
        binding.testModeContainer.setOnClickListener {
            val newState = !localStorage.isTestModeEnabled()
            localStorage.setTestModeEnabled(newState)
            binding.testModeSwitch.isChecked = newState
        }
    }
}

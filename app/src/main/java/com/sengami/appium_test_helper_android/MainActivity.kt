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

        App.sharedPreferences?.let { sharedPreferences ->
            binding.testModeSwitch.isChecked = sharedPreferences.getBoolean(
                Consts.TEST_MODE_ENABLED,
                false
            )
            binding.testModeContainer.setOnClickListener {
                val newState = !binding.testModeSwitch.isChecked
                sharedPreferences
                    .edit()
                    .putBoolean(Consts.TEST_MODE_ENABLED, newState)
                    .commit()
                binding.testModeSwitch.isChecked = newState
            }
        }
    }
}

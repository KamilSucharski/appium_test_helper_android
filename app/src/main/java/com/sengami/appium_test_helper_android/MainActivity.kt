package com.sengami.appium_test_helper_android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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

        localStorage
            .getPackageNames()
            .joinToString(separator = "\n")
            .run(binding.packageNamesEditText::setText)

        binding.savePackageNamesButton.setOnClickListener {
            binding
                .packageNamesEditText
                .text
                .split("\n")
                .let { packageNames ->
                    localStorage.setPackageNames(packageNames)
                    packageNames.forEach { packageName ->
                        grantUriPermission(
                            packageName.toLowerCase(Locale.getDefault()),
                            Uri.parse(Consts.CONTENT_PROVIDER_URI),
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    }
                }
        }
    }
}

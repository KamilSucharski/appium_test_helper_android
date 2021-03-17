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
            .joinToString(separator = Consts.PACKAGE_NAME_SEPARATOR)
            .run(binding.packageNamesEditText::setText)

        saveCurrentPackageNames(binding, localStorage)

        binding.savePackageNamesButton.setOnClickListener {
            saveCurrentPackageNames(binding, localStorage)
        }
    }

    private fun saveCurrentPackageNames(binding: ActivityMainBinding, localStorage: LocalStorage) {
        binding
            .packageNamesEditText
            .text
            .split(Consts.PACKAGE_NAME_SEPARATOR)
            .map { it.trim().toLowerCase(Locale.getDefault()) }
            .let { packageNames ->
                localStorage.setPackageNames(packageNames)
                packageNames.forEach { packageName ->
                    grantUriPermission(
                        packageName,
                        Uri.parse(Consts.CONTENT_PROVIDER_URI),
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                }
            }

        Toast.makeText(
            this,
            R.string.message_package_names_set,
            Toast.LENGTH_SHORT
        ).show()
    }
}

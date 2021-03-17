package com.sengami.appium_test_helper_android

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log

class TestModeContentProvider : ContentProvider() {

    override fun query(
        uri: Uri,
        strings: Array<String>?,
        s: String?,
        strings1: Array<String>?,
        s1: String?
    ): Cursor? {
        return context?.run {
            val localStorage = LocalStorage.fromContext(this)
            val testModeEnabled = localStorage.isTestModeEnabled()
            val cursor = MatrixCursor(arrayOf(Consts.KEY_TEST_MODE_ENABLED))
            cursor.addRow(arrayOf(testModeEnabled.toString()))
            cursor
        }
    }

    override fun getType(uri: Uri): String {
        return Consts.CONTENT_PROVIDER_TYPE
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        s: String?,
        strings: Array<String>?
    ): Int {
        return 0
    }
}

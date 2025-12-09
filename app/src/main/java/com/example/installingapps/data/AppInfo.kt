package com.example.installingapps.data

import android.graphics.Bitmap

data class AppInfo(
    val uid: Int,
    val icon: Bitmap?,
    val title: String,
    val packageName: String = "",
    val versionName: String? = null,
    val checksum: String = "",
)
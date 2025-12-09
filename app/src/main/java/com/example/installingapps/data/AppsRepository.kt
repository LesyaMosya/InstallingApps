package com.example.installingapps.data

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

interface AppsRepository {
    fun getApplicationLabel(pm: PackageManager, appInfo: ApplicationInfo): String {
        val label = pm.getApplicationLabel(appInfo)
        return if (label.isNotBlank()) label.toString() else "Unknown"
    }
}
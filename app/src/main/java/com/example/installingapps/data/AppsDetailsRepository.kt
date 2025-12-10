package com.example.installingapps.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.security.MessageDigest
import javax.inject.Inject

@ViewModelScoped
class AppsDetailsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val packageName: String
) : AppsRepository {
    private val pm = context.packageManager


    suspend fun getAppsDetails(): Flow<AppInfo> {
        val appInfo = pm.getApplicationInfo(packageName, 0)

        val scope = CoroutineScope(Dispatchers.Default)
        val checksum = scope.async {
            val file = File(appInfo.publicSourceDir)
            file.md5()
        }

        val app =
            AppInfo(
                uid = appInfo.uid,
                icon = null,
                title = getApplicationLabel(pm, appInfo),
                packageName = appInfo.packageName,
                versionName = pm.getPackageInfo(appInfo.packageName, 0).versionName,
                checksum = checksum.await(),
            )

        scope.cancel()
        return flow {
            emit(app)
        }
    }

    fun startApp() {
        val intent = pm.getLaunchIntentForPackage(packageName)
        if (intent != null) context.startActivity(intent)
        else throw Exception("Intent haven't be null")
    }

    private fun File.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return this.inputStream().use { fis ->
            val buffer = ByteArray(8192)
            generateSequence {
                when (val bytesRead = fis.read(buffer)) {
                    -1 -> null
                    else -> bytesRead
                }
            }.forEach { bytesRead -> md.update(buffer, 0, bytesRead) }
            md.digest().joinToString("") { "%02x".format(it) }
        }
    }
}
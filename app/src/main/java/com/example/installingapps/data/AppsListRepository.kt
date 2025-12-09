package com.example.installingapps.data

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.text.format.Formatter.formatFileSize
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class AppsListRepository @Inject constructor(
    @ApplicationContext private val context: Context
): AppsRepository {
    private val pm = context.packageManager

    fun getInstalledApps(): Flow<List<AppInfo>> {

        val packages: List<ApplicationInfo?> =
            pm.getInstalledApplications(PackageManager.GET_META_DATA)
        val listApps = mutableListOf<AppInfo>()

        for (appInfo in packages) {
            if (appInfo != null) {

                if (!isSystemPackage(appInfo)) {

                    val newApp =
                        AppInfo(
                            uid = appInfo.uid,
                            icon = getApplicationIcon(appInfo),
                            title = getApplicationLabel(pm, appInfo),
                            packageName = appInfo.packageName
                        )

                    listApps.add(newApp)
                }

            }
        }
        return flow {
            emit(listApps)
        }
    }

    private fun getApplicationIcon(appInfo: ApplicationInfo): Bitmap? {
        val drawable = pm.getApplicationIcon(appInfo.packageName)
        return getBitmapFromDrawable(drawable)
    }

    private fun getBitmapFromDrawable(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) drawable.bitmap
        if (drawable is AdaptiveIconDrawable) {

            val backgroundDrawable: Drawable? = drawable.background
            val foregroundDrawable: Drawable? = drawable.foreground

            val drawableArray = arrayOfNulls<Drawable>(2)
            /*            drawableArray[0] = backgroundDrawable*/
            drawableArray[1] = foregroundDrawable


            val layerDrawable = LayerDrawable(drawableArray)

            val bitmap = Bitmap.createBitmap(
                layerDrawable.intrinsicWidth,
                layerDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            layerDrawable.setBounds(0, 0, canvas.width, canvas.height)
            layerDrawable.draw(canvas)

            return bitmap
        } else return null
    }

    private fun isSystemPackage(appInfo: ApplicationInfo): Boolean {
        return ((appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0)
    }
}
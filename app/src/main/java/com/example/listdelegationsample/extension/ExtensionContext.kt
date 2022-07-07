package com.example.listdelegationsample.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.provider.Settings
import androidx.annotation.DimenRes
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*


@SuppressLint("HardwareIds")
fun Context.deviceId(): String =
    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

fun Context.color(id: Int): Int = ContextCompat.getColor(this, id)

fun Context.deviceName() = Settings.Global.getString(this.contentResolver, "device_name")

fun Context.dimenPxSize(
    @DimenRes dimenResId: Int
): Int = resources.getDimensionPixelSize(dimenResId)

fun Context.getFileSize(uri: Uri): Long {
    if (uri.scheme.equals("content")) {
        this.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            cursor.moveToFirst()
            return cursor.getLong(sizeIndex)
        }
    } else {
        return uri.path?.let { File(it).length() } ?: 0
    }
    return 0
}

fun Context.getFileName(uri: Uri): String? {
    if (uri.scheme.equals("content")) {
        this.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            return cursor.getString(nameIndex)
        }
    } else {
        return uri.path?.substring((uri.path?.lastIndexOf('/') ?: 0) + 1)
    }
    return null
}

fun Context.getFile(uri: Uri?): File? {
    if (uri == null) {
        return null
    }
    try {
        val fis: InputStream? = contentResolver?.openInputStream(uri)
        val file = File(cacheDir, "temp" + Date().time)
        val fos = FileOutputStream(file)
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var length: Int
        var firstPartDataLength = 0
        while (fis?.read(buffer).also { length = (it ?: 0) } ?: 0 > 0) {
            if (firstPartDataLength == 0) {
                firstPartDataLength = length
            }
            fos.write(buffer, 0, length)
        }
        fis?.close()
        fos.close()
        return if (firstPartDataLength != 0) {
            file
        } else {
            null
        }
    } catch (e: IOException) {
        return null
    }
}

fun Context.removeFile(file: File) {
    file.delete()
    if (file.exists()) {
        file.canonicalFile.delete()
        if (file.exists()) {
            deleteFile(file.name)
        }
    }
}

fun Context.openLinkInBrowser(url: String?, noAppCallback: () -> Unit = {}) {
    try {
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(
                if (url?.contains("http://") == false && !url.contains("https://")) "https://$url" else url
                    ?: ""
            )
        )
        startActivity(browserIntent)
    } catch (Ae: ActivityNotFoundException) {
        noAppCallback.invoke()
    }
}

fun Context.areNotificationsEnabled() =
    NotificationManagerCompat.from(this).areNotificationsEnabled()

fun Context.getActivityContext(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> this.baseContext.getActivityContext()
    else -> null
}

fun Context.callToPhone(phone: String) {
    startActivity(
        Intent(
            Intent.ACTION_DIAL,
            Uri.parse(
                "tel:$phone"
            )
        )
    )
}
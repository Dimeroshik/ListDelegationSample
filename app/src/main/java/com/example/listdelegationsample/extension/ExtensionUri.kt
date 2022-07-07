package com.example.listdelegationsample.extension

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileInputStream
import java.io.InputStream


fun Uri.copyToCache(context: Context): File? {
    try {
        context.contentResolver.openFileDescriptor(this, "r", null)?.let { parcelFileDescriptor ->
            FileInputStream(parcelFileDescriptor.fileDescriptor).use { input ->
                return File.createTempFile(
                    "temp",
                    ".${this.getExtension(context)}",
                    context.cacheDir
                ).apply {
                    outputStream().use {
                        input.copyTo(it)
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun Uri.getExtension(context: Context): String? = when (this.scheme) {
    ContentResolver.SCHEME_CONTENT -> {
        MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(this))
    }
    else -> {
        this.path?.let { MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(it)).toString()) }
    }
}

fun String.deleteFile() {
    File(this).apply {
        if (exists()) {
            delete()
        }
    }
}

fun String?.toUri() = this?.let {
    Uri.fromFile(File(this))
}

fun InputStream.copyToCache(
    extension: String?,
    cacheDir: File,
    fileName: String? = null
): File? {
    try {
        return File.createTempFile(
            fileName ?: "temp_",
            ".${extension}",
            cacheDir
        ).apply {
            outputStream().use {
                this@copyToCache.copyTo(it)
                it.close()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun InputStream.copyToCache(
    file: File
): File? {
    try {
        return file.apply {
            outputStream().use {
                this@copyToCache.copyTo(it)
                it.close()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
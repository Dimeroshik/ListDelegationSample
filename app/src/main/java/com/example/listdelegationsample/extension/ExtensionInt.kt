package com.example.listdelegationsample.extension

import android.content.res.Resources

fun Int.pxToDp(density: Float): Int = (this / density).toInt()

fun Int.dpToPx(density: Float): Int = (this * density).toInt()
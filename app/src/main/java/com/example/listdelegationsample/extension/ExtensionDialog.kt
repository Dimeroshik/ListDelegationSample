package com.example.listdelegationsample.extension

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.Window
import androidx.annotation.RequiresApi


@RequiresApi(api = Build.VERSION_CODES.M)
fun Dialog.setWhiteNavigationBar(dialog: Dialog = this) {
    val window: Window? = dialog.window
    val metrics = DisplayMetrics()
    window?.windowManager?.defaultDisplay?.getMetrics(metrics)
    val dimDrawable = GradientDrawable()
    // ...customize your dim effect here
    val navigationBarDrawable = GradientDrawable()
    navigationBarDrawable.shape = GradientDrawable.RECTANGLE
    navigationBarDrawable.setColor(Color.WHITE)
    val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)
    val windowBackground = LayerDrawable(layers)
    windowBackground.setLayerInsetTop(1, metrics.heightPixels)
    window?.setBackgroundDrawable(windowBackground)
}

@RequiresApi(api = Build.VERSION_CODES.M)
fun Dialog.setColorNavigationBar(colorId: Int, dialog: Dialog = this) {
    val window: Window? = dialog.window
    val metrics = DisplayMetrics()
    window?.windowManager?.defaultDisplay?.getMetrics(metrics)
    val dimDrawable = GradientDrawable()
    // ...customize your dim effect here
    val navigationBarDrawable = GradientDrawable()
    navigationBarDrawable.shape = GradientDrawable.RECTANGLE
    navigationBarDrawable.setColor(context.getColor(colorId))
    val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)
    val windowBackground = LayerDrawable(layers)
    windowBackground.setLayerInsetTop(1, metrics.heightPixels)
    window?.setBackgroundDrawable(windowBackground)
}
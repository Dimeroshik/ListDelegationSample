package com.example.listdelegationsample.extension

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.example.listdelegationsample.extension.visible


fun TextView.getTextColorAnimation(
    @ColorRes startColor: Int,
    @ColorRes endColor: Int,
    duration: Long = 100L,
    repeatCount: Int = 0
): Animator =
    ObjectAnimator.ofInt(
        this,
        "textColor",
        ContextCompat.getColor(this.context, startColor),
        ContextCompat.getColor(this.context, endColor)
    ).apply {
        setEvaluator(ArgbEvaluator())
        this.duration = duration
        this.repeatCount = repeatCount
        repeatMode = ObjectAnimator.REVERSE
    }

fun TextView.endDrawable(@DrawableRes id: Int = 0) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0)
}

fun TextView.setTextOrHide(text: String?) {
    this.visible(!text.isNullOrEmpty())
    this.text = text
}

fun TextView.setTextOrHide(text: SpannableStringBuilder?) {
    this.visible(!text.isNullOrEmpty())
    this.text = text
}

fun TextView.setTextOrHide(text: Spanned?) {
    this.visible(!text.isNullOrEmpty())
    this.text = text
}

fun TextView.setTextOrHide(@StringRes resId: Int?) {
    this.visible(resId != null)
    resId?.let {
        this.text = context.getString(it)
    }
}

fun TextView.setTextOrHide(@StringRes resId: Int, text: String?) {
    this.visible(!text.isNullOrEmpty())
    this.text = context.getString(resId, text)
}

fun TextView.setTextAndShow(@StringRes resId: Int) {
    this.text = context.getString(resId)
    this.visible()
}

fun TextView.setTextAndShow(text: String?) {
    this.text = text
    this.visible()
}

fun TextView.startDrawable(@DrawableRes id: Int = 0) {
    this.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
}

fun TextView.disabledAutoSize() {
    TextViewCompat.setAutoSizeTextTypeWithDefaults(
        this,
        TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE
    )
}

fun TextView.enableAutoSize(minSize: Int? = 0, maxSize: Int? = 0, step: Int? = 1) {
    if (minSize ?: 0 > 0 && maxSize ?: 0 > 0 && step ?: 0 > 0)
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
            this, minSize ?: 0, maxSize ?: 0, step ?: 0,
            TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
        )
    else {
        TextViewCompat.setAutoSizeTextTypeWithDefaults(
            this,
            TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
        )
    }
}

fun TextView.setTextSize(@DimenRes resId: Int) =
    this.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(resId).toFloat())

fun TextView.setTextColor(
    @ColorRes normal: Int,
    @ColorRes pressed: Int,
) {
    setTextColor(
        ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_pressed),
                intArrayOf()
            ),
            intArrayOf(
                context.color(pressed),
                context.color(normal)
            )
        )
    )
}

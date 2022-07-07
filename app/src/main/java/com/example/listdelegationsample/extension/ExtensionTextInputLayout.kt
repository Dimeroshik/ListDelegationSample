package com.example.listdelegationsample.extension

import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.example.listdelegationsample.extension.visible


fun TextInputLayout.setTypeface(@FontRes id: Int) {
    typeface = ResourcesCompat.getFont(context, id)
}

fun TextInputLayout.showError(@StringRes resId: Int, isDisableAfterChange: Boolean = true) {
    showError(context.resources.getString(resId), isDisableAfterChange)
}

fun TextInputLayout.setTextOrHide(text: String?) {
    this.visible(!text.isNullOrEmpty())
    this.editText?.setText(text)
}

fun TextInputLayout.showError(value: String?, isDisableAfterChange: Boolean = true) {
    value?.let {
        error = it
        if (isDisableAfterChange) {
            editText?.doAfterTextChanged {
                isErrorEnabled = false
            }
        }
    }
}
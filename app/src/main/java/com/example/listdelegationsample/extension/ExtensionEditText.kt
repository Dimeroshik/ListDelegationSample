package com.example.listdelegationsample.extension

import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.core.widget.doAfterTextChanged

fun EditText.onDone(f: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            f.invoke()
            true
        }else{
            false
        }
    }
}

fun EditText.preventDeleting(@StringRes resId: Int) {
    preventDeleting(context.getString(resId))
}

fun EditText.preventDeleting(value: String) {
    doAfterTextChanged {
        if (text?.length ?: value.length < value.length) {
            setText(value)
            setSelection(value.length)
        }
    }
    accessibilityDelegate = object : View.AccessibilityDelegate() {
        override fun sendAccessibilityEvent(host: View?, eventType: Int) {
            super.sendAccessibilityEvent(host, eventType)
            if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {
                if (selectionStart < value.length) {
                    setSelection(value.length)
                }
            }
        }
    }
}
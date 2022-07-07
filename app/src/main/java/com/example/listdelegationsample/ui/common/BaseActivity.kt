package com.example.listdelegationsample.ui.common

import androidx.annotation.LayoutRes
import com.example.listdelegationsample.extension.onShowSnack
import com.example.listdelegationsample.presentation.view.BaseMvpView
import moxy.MvpAppCompatActivity


abstract class BaseActivity(@LayoutRes layoutResId: Int) : MvpAppCompatActivity(layoutResId),
    BaseMvpView {

    override fun showSnack(resId: Int) {
        showSnack(getString(resId))
    }

    override fun showSnack(message: String) {
        onShowSnack(
            message
        )
    }

    override fun showProgressView() {

    }

    override fun hideProgressView() {

    }

    override fun onNavControllerUp() {

    }

    override fun showAlertDialog(
        titleRes: Int?,
        messageRes: Int?,
        positiveRes: Int,
        negativeRes: Int?,
        messageValue: String?,
        onPositive: (() -> Unit)?,
        onNegative: (() -> Unit)?,
        onDismiss: (() -> Unit)?,
        onCancel: (() -> Unit)?
    ) {
    }

    override fun hideKeyBoard() {

    }
}
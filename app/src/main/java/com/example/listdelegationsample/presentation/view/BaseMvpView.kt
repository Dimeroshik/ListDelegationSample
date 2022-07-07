package com.example.listdelegationsample.presentation.view

import androidx.annotation.StringRes
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

@StateStrategyType(OneExecutionStateStrategy::class)
interface BaseMvpView : MvpView {

    fun showSnack(
        @StringRes resId: Int
    )

    fun showSnack(
        message: String
    )

    @Skip
    fun showProgressView()

    @Skip
    fun hideProgressView()

    fun showAlertDialog(
        @StringRes titleRes: Int? = null,
        @StringRes messageRes: Int? = null,
        @StringRes positiveRes: Int = android.R.string.ok,
        @StringRes negativeRes: Int? = null,
        messageValue: String? = null,
        onPositive: (() -> Unit)? = null,
        onNegative: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null,
        onCancel: (() -> Unit)? = null
    )

    fun onNavControllerUp()

    fun hideKeyBoard()
}
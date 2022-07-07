package com.example.listdelegationsample.extension

import android.app.Activity
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.example.listdelegationsample.R
import com.google.android.material.snackbar.Snackbar


fun Activity.currentDestinationId(): Int? =
    findNavController(R.id.nav_host_fragment).currentDestination?.id

fun Activity.navigate(destination: NavDirections, @IdRes viewId: Int = R.id.nav_host_fragment) =
    with(findNavController(viewId)) {
        currentDestination?.getAction(destination.actionId)?.let {
            navigate(destination)
        }
    }

fun Activity.navigate(@IdRes actionId: Int, @IdRes viewId: Int = R.id.nav_host_fragment) =
    with(findNavController(viewId)) {
        currentDestination?.getAction(actionId)?.let {
            navigate(actionId)
        }
    }

fun Activity.onShowSnack(
    message: String
): Snackbar? {
    if (message.isNotEmpty()) {
        return Snackbar.make(
            findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        )
    }
    return null
}
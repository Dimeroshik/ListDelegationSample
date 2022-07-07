package com.example.listdelegationsample.extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import com.example.listdelegationsample.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import com.example.listdelegationsample.extension.hideSoftKeyboard
import com.example.listdelegationsample.extension.showSoftKeyboard

fun Fragment.navigate(destination: NavDirections) = with(findNavController()) {
    currentDestination?.getAction(destination.actionId)?.let {
        navigate(destination)
    }
}

fun Fragment.navigate(
    destination: NavDirections,
    navigatorExtras: Navigator.Extras
) = with(findNavController()) {
    currentDestination?.getAction(destination.actionId)?.let {
        navigate(destination, navigatorExtras)
    }
}

fun Fragment.hostNavigate(destination: NavDirections) =
    with(Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)) {
        currentDestination?.getAction(destination.actionId)?.let {
            navigate(destination)
        }
    }

fun Fragment.navigateUp() = findNavController().navigateUp()

fun Fragment.navigateUpNum(id: Int = 2) = findNavController().apply {
    popBackStack()
    navigateUp()
}

fun Fragment.navigateUpTo(destination: Int) = findNavController().apply {
    popBackStack(destination, true)
    navigateUp()
}

fun Fragment.hideSoftKeyboard(requestParentFocus: Boolean = false) =
    requireActivity().currentFocus?.hideSoftKeyboard(
        requestParentFocus = requestParentFocus
    )

fun Fragment.showSoftKeyboard() =
    requireActivity().currentFocus?.showSoftKeyboard()

fun Fragment.openUri(uri: Uri?, notFoundAction: () -> Unit) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        data = uri
    }
    if (intent.resolveActivity(requireActivity().packageManager) == null) {
        notFoundAction.invoke()
    } else {
        startActivity(intent);
    }
}

fun Fragment.onShowSnack(
    message: String
): Snackbar? {
    if (message.isNotEmpty()) {
        activity?.let {
            return Snackbar.make(
                it.findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG
            )
        }
    }
    return null
}

fun Fragment.alertDialog(
    @StringRes titleRes: Int? = null,
    @StringRes messageRes: Int? = null,
    @StringRes positiveRes: Int = android.R.string.ok,
    @StringRes negativeRes: Int? = null,
    messageValue: String? = null,
    onPositive: (() -> Unit)? = null,
    onNegative: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
): AlertDialog = MaterialAlertDialogBuilder(requireContext()).apply {
    titleRes?.let {
        setTitle(it)
    }
    setMessage(messageValue ?: messageRes?.let { getString(it) })
    negativeRes?.let {
        setNegativeButton(getString(it)) { _, _ ->
            onNegative?.invoke()
        }
    }
    setPositiveButton(getString(positiveRes)) { _, _ ->
        onPositive?.invoke()
    }
    setOnCancelListener {
        onCancel?.invoke()
    }
    setOnDismissListener {
        onDismiss?.invoke()
    }
}.show()

fun Fragment.setUiSoftKeyboardListener(view: View? = this.view, f: (() -> Unit)? = null) {
    view?.let {
        if (view is EditText) {
            view.setOnFocusChangeListener { _, hasFocus ->
                when (hasFocus) {
                    false -> {
                        hideSoftKeyboard(true)
                        f?.invoke()
                    }
                }
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                setUiSoftKeyboardListener(view.getChildAt(i), f)
            }
        }
    }
}

//fun Fragment.showUpperInfoMessage(
//    messageId: Int,
//    drawableId: Int? = R.drawable.ic_ok,
//    infoMode: Boolean = true
//): MaterialTextView {
//    val view = MessageInfoBinding.inflate(LayoutInflater.from(context))
//
//    view.apply {
//        tvInfoMessage.text = getString(messageId).also {
//            val contains = it.contains("\n")
//            if (contains) root.setBackgroundResource(R.drawable.bg_r_18)
//        }
//
//        drawableId?.let {
//            tvInfoMessage.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
//        }
//
//        tvInfoMessage.background.setTint(
//            ContextCompat.getColor(
//                requireContext(), when (infoMode) {
//                    true -> R.color.primaryColor
//                    false -> R.color.white
//                }
//            )
//        )
//
//        if (!infoMode) tvInfoMessage.setTextColor(
//            ContextCompat.getColor(
//                requireContext(),
//                R.color.red_ff
//            )
//        )
//
//
//        root.apply {
//            val infoMessaveView = tvInfoMessage
//
//            Toast(requireContext()).apply {
//                setGravity(Gravity.TOP, 0, 100)
//                duration = Toast.LENGTH_LONG
//                this.view = root
//            }.show()
//
////            layoutParams = ViewGroup.LayoutParams(
////                ViewGroup.LayoutParams.WRAP_CONTENT,
////                ViewGroup.LayoutParams.WRAP_CONTENT
////            ).apply {
////                gravity = Gravity.CENTER_HORIZONTAL
////            }
////
////            root.elevation = 20F
////
////            rootFragment.addView(infoMessaveView, rootFragment.childCount - 1)
////
////            ViewCompat.setElevation(infoMessaveView, 20F)
////
////            ConstraintSet().apply {
////                clone(rootFragment)
////                connect(
////                    infoMessaveView.id,
////                    ConstraintSet.TOP,
////                    rootFragment.id,
////                    ConstraintSet.TOP,
////                    100
////                )
////                connect(infoMessaveView.id, ConstraintSet.LEFT, rootFragment.id, ConstraintSet.LEFT)
////                connect(
////                    infoMessaveView.id,
////                    ConstraintSet.RIGHT,
////                    rootFragment.id,
////                    ConstraintSet.RIGHT
////                )
////            }.applyTo(rootFragment)
////
////            object : CountDownTimer(4000, 4000) {
////                override fun onTick(millisUntilFinished: Long) {}
////
////                override fun onFinish() {
////                    infoMessaveView.animate()
////                        .alpha(0F)
////                        .setDuration(500)
////                        .withEndAction {
////                            infoMessaveView.visibility = View.GONE
////
////                            rootFragment.removeView(infoMessaveView)
////                        }
////                }
////            }.start()
//
//            return infoMessaveView
//        }
//    }
//}
//
//fun Fragment.showLoader(
//    rootFragment: ConstraintLayout
//): ConstraintLayout {
//    val view = ViewLoaderBinding.inflate(LayoutInflater.from(context))
//
//    view.apply {
//        root.apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//
//            var loader = root
//            rootFragment.addView(root)
//
//            with(lotieLoader) {
//                if (isAnimating) {
//                    pauseAnimation()
//                    progress = 0F
//                }
//                setAnimation("spinner.json")
//                playAnimation()
//                repeatCount = LottieDrawable.INFINITE
//            }
//
//            root.elevation = 10F
//            root.setOnTouchListener { v, event ->
//                true
//            }
//            return loader
//        }
//    }
//}

fun Fragment.sharedPreferences(name: String = R.string.app_name.toString()) =
    context?.getSharedPreferences(name, Context.MODE_PRIVATE)

fun Fragment.openMap(latitude: Float, longtitude: Float) {
    val uri = Uri.parse("geo:0,0?q=$latitude,$longtitude(Maninagar)")
    val intent = Intent(Intent.ACTION_VIEW, uri);
    startActivity(intent);
}
//
//fun Fragment.openContactUsDialog(service: Service){
//    ContactUsDialog(
//        service,
//        onClick = {
//            when{
//                it.length == 11 -> context?.callToPhone(it)
//                it.contains("t.me") -> openUri(Uri.parse(it)){}
//            }
//        }
//    ).show(childFragmentManager, "TAG")
//}
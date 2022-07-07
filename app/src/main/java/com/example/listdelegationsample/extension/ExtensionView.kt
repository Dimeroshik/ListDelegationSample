package com.example.listdelegationsample.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.view.*
import android.view.animation.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.constraintlayout.widget.Group
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.transition.Fade
import androidx.transition.TransitionManager
import com.example.listdelegationsample.extension.addListener
import com.example.listdelegationsample.extension.dpToPx
import com.google.android.material.internal.ViewUtils

@SuppressLint("RestrictedApi")
fun View.addSystemBottomPadding(
    targetView: View = this
) {
    ViewUtils.doOnApplyWindowInsets(
        targetView
    ) { _, insets, initialPadding ->
        targetView.updatePadding(
            bottom = initialPadding.bottom + insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()).bottom
        )
        insets
    }
}

@SuppressLint("RestrictedApi")
fun View.addSystemBottomPaddingKeyboard(
    targetView: View = this
) {
    ViewUtils.doOnApplyWindowInsets(
        targetView
    ) { _, insets, initialPadding ->
        targetView.updatePadding(
            bottom = initialPadding.bottom + insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
        )
        insets
    }
}

@SuppressLint("RestrictedApi")
fun View.addSystemTopPadding(
    targetView: View = this
) {
    ViewUtils.doOnApplyWindowInsets(
        targetView
    ) { _, insets, initialPadding ->
        targetView.updatePadding(
            top = initialPadding.top + insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
        )
        insets
    }
}

@SuppressLint("RestrictedApi")
fun View.addSystemPaddings(
    targetView: View = this
) {
    ViewUtils.doOnApplyWindowInsets(
        targetView
    ) { _, insets, initialPadding ->
        targetView.updatePadding(
            top = initialPadding.top + insets.getInsets(WindowInsetsCompat.Type.statusBars()).top,
            bottom = initialPadding.bottom + insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()).bottom
        )
        insets
    }
}

@SuppressLint("RestrictedApi")
fun View.addSystemTopMargin(
    initialMargin: Int = 0
) {
    ViewUtils.doOnApplyWindowInsets(
        this
    ) { _, insets, _ ->
        this.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = (initialMargin).dpToPx(resources.displayMetrics.density) + insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
        }
        insets
    }
}

@SuppressLint("RestrictedApi")
fun View.addSystemBottomMargin(
    @DimenRes initialMargin: Int = 0,
    targetView: View = this
) {
    ViewUtils.doOnApplyWindowInsets(
        targetView
    ) { _, insets, initialPadding ->

        targetView.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = when (initialMargin) {
                0 -> {
                    insets.systemWindowInsetBottom
                }
                else -> {
                    resources.getDimensionPixelSize(initialMargin) + insets.systemWindowInsetBottom
                }
            }
        }
        insets
    }
}



inline fun View.afterMeasured(crossinline f: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun TextView.hideWhenEmpty(value: String?) {
    text = value
    isVisible = text.isNotBlank()
}

inline var View.isVisibleInvisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.INVISIBLE
    }

fun View.hideSoftKeyboard(view: View = this, requestParentFocus: Boolean = false) {
    val inputMethodManager =
        view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    view.clearFocus()
    when (requestParentFocus) {
        true -> {
            (view.parent as View).requestFocus()
        }
    }
}

fun View.showSoftKeyboard(view: View = this, delayInMillis: Long = 200L) {
    if (view.requestFocus()) {
        view.postDelayed(delayInMillis) {
            val inputMethodManager =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

fun View.restartInput(view: View = this) {
    val inputMethodManager =
        view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.restartInput(view)
}

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            // We've found a CoordinatorLayout, use it
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                // If we've hit the decor content view, then we didn't find a CoL in the
                // hierarchy, so use it.
                return view
            } else {
                // It's not the content view but we'll use it as our fallback
                fallback = view
            }
        }

        if (view != null) {
            // Else, we will loop and crawl up the view hierarchy and try to find a parent
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
    return fallback
}

fun View.getChilds(): List<View> {
    val childs = mutableListOf<View>()
    if (this is ViewGroup) {
        for (i in 0 until this.childCount) {
            childs.add(this.getChildAt(i))
        }
    }
    return childs
}

fun ViewGroup.getEditTexts(): List<EditText> {
    val childs = mutableListOf<EditText>()
    for (i in 0 until this.childCount) {
        if (this.getChildAt(i) is ViewGroup) {
            childs.addAll(
                (this.getChildAt(i) as ViewGroup)
                    .getChilds()
                    .filterIsInstance(EditText::class.java)
            )
        }
    }
    return childs
}

fun View.fadeOut(
    durationAnimation: Long = 200L,
    isGone: Boolean = true,
    onEnd: (() -> Unit)? = null
) {
    cancelAnimation()
    when (visibility) {
        View.VISIBLE -> {
            startAnimation(AlphaAnimation(1f, 0f).apply {
                duration = durationAnimation
                interpolator = AccelerateInterpolator()
                addListener(
                    onEnd = {
                        this@fadeOut.visibility = when (isGone) {
                            true -> View.GONE
                            else -> View.INVISIBLE
                        }
                        onEnd?.invoke()
                    }
                )
            })
        }
        View.INVISIBLE -> {
            this@fadeOut.visibility = when (isGone) {
                true -> View.GONE
                else -> View.INVISIBLE
            }
            onEnd?.invoke()
        }
        View.GONE -> {
            onEnd?.invoke()
        }
    }
}

fun View.fadeIn(durationAnimation: Long = 200L, onEnd: (() -> Unit)? = null) {
    cancelAnimation()
    when (visibility) {
        View.GONE -> {
            startAnimation(AlphaAnimation(0f, 1f).apply {
                duration = durationAnimation
                interpolator = AccelerateInterpolator()
                addListener(
                    onStart = {
                        this@fadeIn.visibility = View.VISIBLE
                    },
                    onEnd = {
                        onEnd?.invoke()
                    }
                )
            })
        }
        View.INVISIBLE -> {
            this@fadeIn.visibility = View.VISIBLE
            onEnd?.invoke()
        }
        View.VISIBLE -> {
            onEnd?.invoke()
        }
    }
}

fun View.cancelAnimation() {
    animation?.setAnimationListener(null)
    animation?.cancel()
    clearAnimation()
}

fun Group.fadeOut(
    durationAnimation: Long = 200L,
    onEnd: (() -> Unit)? = null,
    visibility: Int = View.INVISIBLE
) {
    this.findSuitableParent()?.let { parent ->
        TransitionManager.endTransitions(parent)
        TransitionManager.beginDelayedTransition(parent, Fade(Fade.OUT).apply {
            duration = durationAnimation
            referencedIds.forEach { id ->
                addTarget(id)
            }
            addListener(
                onEnd = {
                    onEnd?.invoke()
                }
            )
        })
        this.visibility = visibility
    }
}

fun Group.fadeIn(
    durationAnimation: Long = 200L,
    onEnd: (() -> Unit)? = null
) {
    this.findSuitableParent()?.let { parent ->
        TransitionManager.endTransitions(parent)
        TransitionManager.beginDelayedTransition(parent, Fade(Fade.IN).apply {
            duration = durationAnimation
            referencedIds.forEach { id ->
                addTarget(id)
            }
            addListener(
                onEnd = {
                    onEnd?.invoke()
                }
            )
        })
        this.visibility = View.VISIBLE
    }
}

fun View.performHaptic(feedbackConstant: Int = HapticFeedbackConstants.LONG_PRESS) {
    performHapticFeedback(feedbackConstant)
}

val View.screenLocation
    get(): IntArray {
        val point = IntArray(2)
        getLocationOnScreen(point)
        return point
    }

inline var View.isFadeVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        when (value) {
            true -> {
                this.fadeIn(200)
            }
            else -> {
                this.fadeOut(200)
            }
        }
    }

fun View.hideKeyboardWhenMove() {
    setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                hideSoftKeyboard()
            }
            MotionEvent.ACTION_UP -> {
                v.performClick()
            }
        }
        false
    }
}

fun ImageView.setSvgColor(@ColorRes color: Int) =
    setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
package com.example.listdelegationsample.extension

import android.animation.ValueAnimator
import android.util.TypedValue
import android.view.animation.Animation
import android.widget.TextView
import androidx.core.animation.addListener

fun Animation.addListener(
    onEnd: ((animation: Animation) -> Unit)? = null,
    onStart: ((animation: Animation) -> Unit)? = null,
    onRepeat: ((animation: Animation) -> Unit)? = null
): Animation.AnimationListener {
    val listener = object : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation) {
            onRepeat?.invoke(animation)
        }

        override fun onAnimationEnd(animation: Animation) {
            onEnd?.invoke(animation)
        }

        override fun onAnimationStart(animation: Animation) {
            onStart?.invoke(animation)
        }
    }
    setAnimationListener(listener)
    return listener
}

fun TextView.setTextSizeWithAnimation(newSizePX: Float, duration: Long, onEnd: (() -> Unit)?=null) {
    val animator = ValueAnimator.ofFloat(textSize, newSizePX).setDuration(duration)
    animator.addUpdateListener {
        val newSize = it.animatedValue as Float
        setTextSize(TypedValue.COMPLEX_UNIT_PX, newSize)
    }
    animator.addListener(
        onEnd = {
            onEnd?.invoke() },
        onCancel = {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, newSizePX)
            onEnd?.invoke()
        }
    )
    animator.start()
}

fun List<TextView>.setTextSizeWithAnimation(newSizePX: Float, duration: Long, onEnd: (() -> Unit)?=null){
    val animator = ValueAnimator.ofFloat(first().textSize, newSizePX).setDuration(duration)
    animator.addUpdateListener {
        val newSize = it.animatedValue as Float
        forEach { view->
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, newSize)
        }
    }
    animator.addListener(
        onEnd = { onEnd?.invoke() },
        onCancel = {
            forEach { view->
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, newSizePX)
            }
            onEnd?.invoke()
        }
    )
    animator.start()
}
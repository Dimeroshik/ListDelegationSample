package com.example.listdelegationsample.extension

import androidx.transition.Transition

fun Transition.addListener(
    onEnd: ((transition: Transition) -> Unit)? = null,
    onStart: ((transition: Transition) -> Unit)? = null,
    onCancel: ((transition: Transition) -> Unit)? = null
) {
    addListener(object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
            onEnd?.invoke(transition)
        }

        override fun onTransitionResume(transition: Transition) {

        }

        override fun onTransitionPause(transition: Transition) {

        }

        override fun onTransitionCancel(transition: Transition) {
            onCancel?.invoke(transition)
        }

        override fun onTransitionStart(transition: Transition) {
            onStart?.invoke(transition)
        }

    })
}
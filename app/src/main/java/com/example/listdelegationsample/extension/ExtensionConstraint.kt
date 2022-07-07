package com.example.listdelegationsample.extension

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet


fun View.constraintSetMatchParent(viewParent: ConstraintLayout): ConstraintSet {
    return ConstraintSet().apply {
        clone(viewParent)
        connect(
            this@constraintSetMatchParent.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            0
        )
        connect(
            this@constraintSetMatchParent.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            0
        )
        connect(
            this@constraintSetMatchParent.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            0
        )
        connect(
            this@constraintSetMatchParent.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )
    }
}
package com.example.listdelegationsample.extension

import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_START
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.paging.PagedListDelegationAdapter
import com.example.listdelegationsample.R


fun RecyclerView.addDivider(
    @DrawableRes id: Int = R.drawable.divider_vertical_20,
    orientation: Int = DividerItemDecoration.VERTICAL
) {
    val divider = DividerItemDecoration(
        context,
        orientation
    )
    ContextCompat.getDrawable(
        context,
        id
    )?.let {
        divider.setDrawable(
            it
        )
    }
    addItemDecoration(divider)
}

inline fun RecyclerView.findLastVisibleItemPosition(
    crossinline f: (Int) -> Unit
) {
    if (layoutManager is LinearLayoutManager) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            val lManager = layoutManager as LinearLayoutManager
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (adapter?.itemCount ?: 0 > 0) {
                    val visiblePosition = lManager.findLastVisibleItemPosition()
                    if (visiblePosition > -1) {
                        f.invoke(visiblePosition)
                    }
                }
            }
        })
    }
}

inline fun RecyclerView.findFirstVisibleItemPosition(
    crossinline f: (Int) -> Unit
) {
    if (layoutManager is LinearLayoutManager) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            val lManager = layoutManager as LinearLayoutManager
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (adapter?.itemCount ?: 0 > 0) {
                    val visiblePosition = lManager.findFirstVisibleItemPosition()
                    if (visiblePosition > -1) {
                        f.invoke(visiblePosition)
                    }
                }
            }
        })
    }
}

inline fun RecyclerView.doOnScroll(
    crossinline onScrolled: (recyclerView: RecyclerView, dx: Int, dy: Int) -> Unit = { _, _, _ -> },
    crossinline onScrollStateChanged: (recyclerView: RecyclerView, newState: Int) -> Unit = { _, _ -> }
) {

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            onScrolled(recyclerView, dx, dy)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            onScrollStateChanged(recyclerView, newState)
        }
    })
}

fun PagedListDelegationAdapter<Any>.preventWhenEmpty() {
    stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
}

fun RecyclerView.smoothScrollTo(
    position: Int,
    speed: Float = 125f,
    firstVisibleItemPosition: Int = 0,
    snap: Int = SNAP_TO_START
) {
    val smoothScroller = object : LinearSmoothScroller(context) {

        override fun getVerticalSnapPreference(): Int {
            return snap
        }

        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
            return speed / when (firstVisibleItemPosition) {
                0 -> 1f
                else -> minOf(firstVisibleItemPosition.toFloat(), speed)
            } / (displayMetrics?.densityDpi ?: 1)
        }
    }
    smoothScroller.targetPosition = position
    (layoutManager as LinearLayoutManager).startSmoothScroll(smoothScroller)
}

fun RecyclerView.addInsertObserver() {
    adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            adapter?.unregisterAdapterDataObserver(this)
            smoothScrollTo(0)
        }
    })
}

fun RecyclerView.addSpaceAtBottom(action: (View)->Unit) {
    this.addItemDecoration(
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val dataSize = state.itemCount
                val position = parent.getChildAdapterPosition(view)
                if (dataSize > 0 && position == dataSize - 1){

                }
            }
        }
    )
}
package com.example.listdelegationsample.extension

import androidx.viewpager.widget.ViewPager

inline fun ViewPager.doOnPageSelected(crossinline action: (position: Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            action(position)
        }
    })
}
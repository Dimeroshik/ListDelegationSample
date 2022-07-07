package com.example.listdelegationsample.extension

import android.util.Patterns


fun String.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()


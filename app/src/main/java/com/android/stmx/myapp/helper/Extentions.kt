package com.android.stmx.myapp.helper

fun Int.addZero(): String {
    return if (this < 10) "0$this" else "$this"
}
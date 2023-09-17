package com.example.todotodo.library

import android.content.Context
import kotlin.math.roundToInt

fun Int.convertDPtoPX(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (density * this).roundToInt()
}
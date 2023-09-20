package com.example.todotodo.library

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, s: String) {
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}
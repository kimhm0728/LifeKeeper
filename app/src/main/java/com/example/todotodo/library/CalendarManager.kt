package com.example.todotodo.library

import java.text.SimpleDateFormat
import java.util.Date

private val dateFormat = SimpleDateFormat("yyyy/MM/dd")
private val postedFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss EEE")

fun dateIntToStr(year: Int, month: Int, day: Int): String {
    var date = "$year/"

    if (month < 10) date += "0"
    date += "$month/"

    if (day < 10) date += "0"
    date += day

    return date
}

fun String.toDate(): Date {
    return dateFormat.parse(this) as Date
}

fun Date.toDateString(): String {
    return dateFormat.format(this)
}

fun Date.toPostedString(): String {
    return postedFormat.format(this)
}
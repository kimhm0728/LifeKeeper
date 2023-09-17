package com.example.todotodo.library

import java.text.SimpleDateFormat
import java.util.Date

fun dateIntToStr(year: Int, month: Int, day: Int): String {
    var date = "$year / "

    if (month < 10) date += "0"
    date += "$month / "

    if (day < 10) date += "0"
    date += day

    return date
}

fun dateStrToDate(str: String): Date {
    return SimpleDateFormat("yyyy / MM / dd").parse(str)
}
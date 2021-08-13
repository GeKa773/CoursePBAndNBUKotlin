package com.gekaradchenko.coursespbandnbukotlin.binding

import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

private const val FORMAT_2 = "%.2f"
private const val FORMAT_3 = "%.3f"
private const val DATE_FORMAT = "dd.MM.yyyy"


@BindingAdapter("setCourse")
fun TextView.setCourse(course: Double) {
    text = String.format(FORMAT_3, course)
}

@BindingAdapter("setCourseNBU")
fun TextView.setCourseNBU(course: Double) {
    text = String.format(FORMAT_2, course)
}

@BindingAdapter("setDate")
fun TextView.setDate(date: Date) {
    val spannable = SpannableString(SimpleDateFormat(DATE_FORMAT).format(date))
    spannable.setSpan(UnderlineSpan(), 0, spannable.length, 0)
    text = spannable
}


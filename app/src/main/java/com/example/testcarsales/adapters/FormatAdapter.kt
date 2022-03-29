package com.example.testcarsales.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.testcarsales.utils.Formats.Companion.formatNumberToString
import com.example.testcarsales.utils.Formats.Companion.formatToString
import java.util.*

@BindingAdapter("number")
fun setNumber(textView: TextView, number: Int) {
    textView.text = number.formatNumberToString()
}

@BindingAdapter("date")
fun setDate(textView: TextView, date: Date?) {
    textView.text = date?.formatToString()
}
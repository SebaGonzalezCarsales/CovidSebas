package com.example.testcarsales.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Formats {
    companion object {
        fun Date.formatToString(): String =
            SimpleDateFormat("dd MMMM yyyy", Locale("es")).format(this)

        fun Int.formatNumberToString(): String =
            DecimalFormat("#,###").format(this)
    }
}
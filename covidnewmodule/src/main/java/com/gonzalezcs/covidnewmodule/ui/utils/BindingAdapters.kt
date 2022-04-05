package com.gonzalezcs.covidnewmodule.ui.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gonzalezcs.covidnewmodule.data.model.FormatCalendarObject

import java.text.NumberFormat

/*
  * @Function: numberFormatTextView
  * @Param intValue (Int)
  * @Param labelValueString (String)
  * @Return: None
  * */
@BindingAdapter("app:numberFormatTextView","app:labelValueString", requireAll = true )
fun TextView.numberFormatTextView(intValue:Int,labelValueString: String) {
    this.text = String.format("%s: %s",labelValueString,NumberFormat.getInstance().format(intValue))
}

/*
  * @Function: dateFormat
  * @Param formatCalendarObject (FormatCalendarObject)
  * @Return: None
  * */
@BindingAdapter("app:dateFormat")
fun TextView.dateFormat(formatCalendarObject: FormatCalendarObject) {
    this.text = ValueFormatClass().getCalendarInstance(
        formatCalendarObject
    ).stringDate
}
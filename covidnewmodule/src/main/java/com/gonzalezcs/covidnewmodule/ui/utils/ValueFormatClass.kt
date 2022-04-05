package com.gonzalezcs.covidnewmodule.ui.utils
import com.gonzalezcs.covidnewmodule.data.model.DataCovidModel
import com.gonzalezcs.covidnewmodule.data.model.FormatCalendarObject
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Int as Int1

/*
This class contains all the functions for string, int or any other value demanded
* */
class ValueFormatClass {

    /*
    * @Function: ValueFormatClass
    * @Param: year (Int)
    * @Param: month (Int)
    * @Param: day (Int)
    * @Return: String (fixed date)
    * */
    fun setCalendarFormat(year: Int1, month: Int1, day: Int1) : String{
        val fixedMonth = if (month < 10) "0"+(month+1).toString() else (month+1).toString()
        val fixedDay = if (day < 10) "0"+(day).toString() else (day).toString()
        return "$year-$fixedMonth-$fixedDay"
    }
    /*
    * @Function: getCalendarInstance
    * @Return: FormatCalendarObject<year, month, day, currentStringDate>
    * */
    fun getCalendarInstance(formateCalendarObject: FormatCalendarObject?): FormatCalendarObject {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH) //get yesterday from current date

        if (formateCalendarObject != null) {
            calendar.set(formateCalendarObject.year,formateCalendarObject.month,formateCalendarObject.day)
        }else{
            day = calendar.get(Calendar.DAY_OF_MONTH)-1 //get yesterday from current date
        }

        val currentStringDate = getDateFormatString(calendar,"d 'de' MMMM 'del' yyyy")

        return FormatCalendarObject(
            year,
            month,
            day,
            currentStringDate
        )
    }
    /*
    * @Function: getDateFormatString
    * @Param: calendar (Calendar)
    * @Param: format (String)
    * @Return: String with date formated
    * */
    fun getDateFormatString(calendar: Calendar,format:String):String{
        return SimpleDateFormat(format).format(calendar.time)
    }

}
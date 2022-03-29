package au.com.carsales.basemodule.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by anibalbastias on 07-09-17.
 * Class for format String in Dates
 */

object DateUtils {

    private const val HOUR = (3600 * 1000).toLong() // in milli-seconds.
    private const val yyyyMMddhhmmssaFormat = "yyyy-MM-dd',' hh:mm:ss a"
    private const val ddMMMMYYFormat = "dd MMMM yy"
    private const val hhmmaFormat = "hh:mm a"
    private const val hhmmaFormatWithoutSpace = "hh.mma"
    private const val ddmmyyyyFormat = "dd/MM/yyyy"
    private const val ddMMYYYYHHMMFormat = "dd/MM/yyyy',' HH:mm"
    private const val ddMMYYYY_HHMMFormat = "dd/MM/yyyy HH:mm"
    private const val MMddYYYYHHMMFormat = "MM/dd/yyyy',' hh':'mm':'ss.SSS a"
    private const val eeeddMMMHHmmaFormat_withComma = "EEE',' dd MMM',' hh:mm a"
    private const val eeeddMMMHHmmaFormat_withoutComma = "EEE dd MMM hh:mm a"
    private const val eeeddMMMyyyyHHmmaFormat = "EEE dd MMM yyyy',' hh:mm a"
    private const val eeeddMMMFormat = "EEE dd MMM"
    private const val eeeddMMMHHmmaFormat_withDashAndSpace = "EEE dd MMM '-' hh:mma"
    private const val yyyyMMddThhmmssZ = "yyyy-MM-dd'T'HH:mm:ss'Z'" // 24H: HH, 12H: hh
    private const val yyyyMMddThhmmssSZ = "yyyy-MM-dd'T'HH:mm:ss.S'Z'"
    private const val yyyyMMddThhmmssSSZ = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'"
    private const val yyyyMMddThhmmssSSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val yyyyMMddThhmmssS = "yyyy-MM-dd'T'HH:mm:ss.S"
    private const val yyyyMMddThhmmssSS = "yyyy-MM-dd'T'HH:mm:ss.SS"
    private const val yyyyMMddThhmmssSSS = "yyyy-MM-dd'T'HH:mm:ss.SSS"

    val nowShorDateTime: String
        get() {
            val format = SimpleDateFormat(eeeddMMMyyyyHHmmaFormat, Locale.ENGLISH)
            format.timeZone = TimeZone.getTimeZone("UTC")
            return format.format(Date())
        }

    fun formatDateTimeToShortDateTime(strCurrentDate: String): String {
        var format = SimpleDateFormat(ddMMYYYYHHMMFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        format = SimpleDateFormat(eeeddMMMHHmmaFormat_withComma, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()

        return format.format(newDate)
    }

    fun formatDateTimeToFollowUps(currentDateSelected: Date): String {
        val format = SimpleDateFormat(eeeddMMMyyyyHHmmaFormat, Locale.ENGLISH)
        return format.format(currentDateSelected)
    }

    fun formatDateTimeToAppointsDateTime(strCurrentDate: String): String {
        var format = SimpleDateFormat(ddMMYYYYHHMMFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            val format1 = SimpleDateFormat(ddMMYYYY_HHMMFormat, Locale.ENGLISH)
            format1.timeZone = TimeZone.getDefault()
            try {
                newDate = format1.parse(strCurrentDate)
            } catch (e1: ParseException) {
                e1.printStackTrace()
            }

        }

        format = SimpleDateFormat(eeeddMMMyyyyHHmmaFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }

    fun getDateFromBruteFormat(strCurrentDate: String): Date? {
        val format = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return newDate
    }

    fun formatBruteDateTimeToAppointsDateTime(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        format = SimpleDateFormat(eeeddMMMyyyyHHmmaFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }

    fun formatStringDateToCalendar(stringInstanceRepresentingDate: String?): Calendar {
        val df = SimpleDateFormat(eeeddMMMyyyyHHmmaFormat, Locale.ENGLISH)
        df.timeZone = TimeZone.getDefault()
        val cal = Calendar.getInstance()

        if (stringInstanceRepresentingDate != null) {
            try {
                cal.time = df.parse(stringInstanceRepresentingDate)
            } catch (e: ParseException) {
                val df1 = SimpleDateFormat(ddmmyyyyFormat, Locale.ENGLISH)
                df.timeZone = TimeZone.getDefault()
                try {
                    cal.time = df1.parse(stringInstanceRepresentingDate)
                } catch (e1: ParseException) {
                    e1.printStackTrace()
                }

            } catch (e: NullPointerException) {
                cal.time = Date()
            }

        } else {
            cal.time = Date()
        }
        return cal
    }

    fun formatDateForAppointments(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH) // yyyyMMdd hh:mm
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        format = SimpleDateFormat(eeeddMMMHHmmaFormat_withComma, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }

    fun formatDateForAppointments(strCurrentDate: Date): String {
        val format = SimpleDateFormat(eeeddMMMHHmmaFormat_withComma, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(strCurrentDate)
    }

    fun formatDateForToDo(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH) // yyyyMMdd hh:mm
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        format = SimpleDateFormat(eeeddMMMHHmmaFormat_withoutComma, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }

    fun getNToNowHoursMoreDateTime(n_hours: Int): String {
        val nowDate = Date()
        val newDate = Date(nowDate.time + n_hours * HOUR)
        val format = SimpleDateFormat(eeeddMMMyyyyHHmmaFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(newDate)
    }

    fun getNToDateHoursMoreDateTime(date: Date, n_hours: Int): String {
        val newDate = Date(date.time + n_hours * HOUR)
        val format = SimpleDateFormat(eeeddMMMyyyyHHmmaFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(newDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun getFormatDay(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH) // yyyyMMdd hh:mm
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            format = SimpleDateFormat(yyyyMMddThhmmssSZ, Locale.ENGLISH) // yyyyMMdd hh:mm
            try {
                newDate = format.parse(strCurrentDate)
            } catch (e1: ParseException) {
                e1.printStackTrace()
            }

            e.printStackTrace()
        }

        format = SimpleDateFormat(eeeddMMMHHmmaFormat_withComma, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun getProspectFormatDay(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH) // yyyyMMdd hh:mm
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            format = SimpleDateFormat(yyyyMMddThhmmssSZ, Locale.ENGLISH) // yyyyMMdd hh:mm
            try {
                newDate = format.parse(strCurrentDate)
            } catch (e1: ParseException) {
                e1.printStackTrace()
            }

            e.printStackTrace()
        }

        format = SimpleDateFormat(eeeddMMMHHmmaFormat_withoutComma, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun getInventoryDetailOverView(strCurrentDate: String): String {
        var format = SimpleDateFormat(ddmmyyyyFormat, Locale.ENGLISH) // yyyyMMdd hh:mm
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {

            e.printStackTrace()
        }

        format = SimpleDateFormat(eeeddMMMHHmmaFormat_withoutComma, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }


    @SuppressLint("SimpleDateFormat")
    fun getPastAppointmentHeaderDateFormat(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH) // yyyyMMdd hh:mm
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            format = SimpleDateFormat(yyyyMMddThhmmssSZ, Locale.ENGLISH) // yyyyMMdd hh:mm
            try {
                newDate = format.parse(strCurrentDate)
            } catch (e1: ParseException) {
                e1.printStackTrace()
            }

            e.printStackTrace()
        }

        format = SimpleDateFormat(eeeddMMMFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }


    fun stringToDateTime(strCurrentDate: String): Date? {
        val format = SimpleDateFormat(yyyyMMddhhmmssaFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return newDate
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateTimeToTime(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddhhmmssaFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
            format = SimpleDateFormat(hhmmaFormat, Locale.ENGLISH)
            return format.format(newDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }

    }

    fun formatAUChatDateTime(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssSSS, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            val format1 = SimpleDateFormat(yyyyMMddThhmmssSS, Locale.ENGLISH)
            try {
                newDate = format1.parse(strCurrentDate)
            } catch (e1: ParseException) {
                val format2 = SimpleDateFormat(yyyyMMddThhmmssS, Locale.ENGLISH)
                try {
                    newDate = format2.parse(strCurrentDate)
                } catch (e2: ParseException) {
                    val format3 = SimpleDateFormat(yyyyMMddThhmmssS, Locale.ENGLISH)
                    try {
                        newDate = format3.parse(strCurrentDate)
                    } catch (e3: ParseException) {
                        return ""
                    }

                }

            }

        }

        if (isToday(newDate)) {
            val messageHour = "Today at "
            val hourFormat = SimpleDateFormat(hhmmaFormat, Locale.ENGLISH)
            hourFormat.timeZone = TimeZone.getDefault()
            return messageHour + hourFormat.format(newDate)
            //            return TimeAgo.using(newDate.getTime());
        } else {
            format = SimpleDateFormat(eeeddMMMHHmmaFormat_withComma, Locale.ENGLISH)
            format.timeZone = TimeZone.getDefault()
            return format.format(newDate)
        }
    }

    fun formatAUChatDateTimeFormatWithDash(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssSSS, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            val format1 = SimpleDateFormat(yyyyMMddThhmmssSS, Locale.ENGLISH)
            try {
                newDate = format1.parse(strCurrentDate)
            } catch (e1: ParseException) {
                val format2 = SimpleDateFormat(yyyyMMddThhmmssS, Locale.ENGLISH)
                try {
                    newDate = format2.parse(strCurrentDate)
                } catch (e2: ParseException) {
                    val format3 = SimpleDateFormat(yyyyMMddThhmmssS, Locale.ENGLISH)
                    try {
                        newDate = format3.parse(strCurrentDate)
                    } catch (e3: ParseException) {
                        val format4 = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH)
                        try {
                            newDate = format4.parse(strCurrentDate)
                        } catch (e4: ParseException) {
                            return ""
                        }

                    }

                }

            }

        }

        if (isToday(newDate)) {
            val messageHour = "Today at "
            val hourFormat = SimpleDateFormat(hhmmaFormat, Locale.ENGLISH)
            hourFormat.timeZone = TimeZone.getDefault()
            return messageHour + hourFormat.format(newDate)
            //            return TimeAgo.using(newDate.getTime());
        } else {
            format = SimpleDateFormat(eeeddMMMHHmmaFormat_withDashAndSpace, Locale.ENGLISH)
            format.timeZone = TimeZone.getDefault()
            return format.format(newDate)
        }
    }

    fun formatAUNowChatDateTime(): String {
        val newDate = Date()
        val format = SimpleDateFormat(yyyyMMddThhmmssSSS, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(newDate)
    }

    fun formatAUNowBruteChatDateTime(): String {
        val newDate = Date()
        val format = SimpleDateFormat(MMddYYYYHHMMFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(newDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatAUDateTime(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssSSSZ, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            //            e.printStackTrace();
            val format1 = SimpleDateFormat(yyyyMMddThhmmssSSZ, Locale.ENGLISH)
            try {
                newDate = format1.parse(strCurrentDate)
            } catch (e1: ParseException) {
                //                e1.printStackTrace();
                val format2 = SimpleDateFormat(yyyyMMddThhmmssSZ, Locale.ENGLISH)
                try {
                    newDate = format2.parse(strCurrentDate)
                } catch (e2: ParseException) {
                    //                    e2.printStackTrace();
                    val format3 = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH)
                    try {
                        newDate = format3.parse(strCurrentDate)
                    } catch (e3: ParseException) {
                        //                        e3.printStackTrace();
                        return ""
                    }

                }

            }

        }

        format = SimpleDateFormat(ddMMMMYYFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatAUHourTimePastAppointment(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssSSSZ, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            //            e.printStackTrace();
            val format1 = SimpleDateFormat(yyyyMMddThhmmssSSZ, Locale.ENGLISH)
            try {
                newDate = format1.parse(strCurrentDate)
            } catch (e1: ParseException) {
                //                e1.printStackTrace();
                val format2 = SimpleDateFormat(yyyyMMddThhmmssSZ, Locale.ENGLISH)
                try {
                    newDate = format2.parse(strCurrentDate)
                } catch (e2: ParseException) {
                    //                    e2.printStackTrace();
                    val format3 = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH)
                    try {
                        newDate = format3.parse(strCurrentDate)
                    } catch (e3: ParseException) {
                        //                        e3.printStackTrace();
                        return ""
                    }

                }

            }

        }

        format = SimpleDateFormat(hhmmaFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatAUHourTime(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssSSSZ, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            //            e.printStackTrace();
            val format1 = SimpleDateFormat(yyyyMMddThhmmssSSZ, Locale.ENGLISH)
            try {
                newDate = format1.parse(strCurrentDate)
            } catch (e1: ParseException) {
                //                e1.printStackTrace();
                val format2 = SimpleDateFormat(yyyyMMddThhmmssSZ, Locale.ENGLISH)
                try {
                    newDate = format2.parse(strCurrentDate)
                } catch (e2: ParseException) {
                    //                    e2.printStackTrace();
                    val format3 = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH)
                    try {
                        newDate = format3.parse(strCurrentDate)
                    } catch (e3: ParseException) {
                        //                        e3.printStackTrace();
                        return ""
                    }

                }

            }

        }

        format = SimpleDateFormat(hhmmaFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatAUHourTimeWithoutSpace(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssSSSZ, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            //            e.printStackTrace();
            val format1 = SimpleDateFormat(yyyyMMddThhmmssSSZ, Locale.ENGLISH)
            try {
                newDate = format1.parse(strCurrentDate)
            } catch (e1: ParseException) {
                //                e1.printStackTrace();
                val format2 = SimpleDateFormat(yyyyMMddThhmmssSZ, Locale.ENGLISH)
                try {
                    newDate = format2.parse(strCurrentDate)
                } catch (e2: ParseException) {
                    //                    e2.printStackTrace();
                    val format3 = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH)
                    try {
                        newDate = format3.parse(strCurrentDate)
                    } catch (e3: ParseException) {
                        //                        e3.printStackTrace();
                        return ""
                    }

                }

            }

        }

        format = SimpleDateFormat(hhmmaFormatWithoutSpace, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate).toLowerCase()
    }

    @SuppressLint("SimpleDateFormat")
    fun formatAUInventoryHourTime(strCurrentDate: String): String {
        var format = SimpleDateFormat(yyyyMMddThhmmssSSSZ, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            //            e.printStackTrace();
            val format1 = SimpleDateFormat(yyyyMMddThhmmssSSZ, Locale.ENGLISH)
            try {
                newDate = format1.parse(strCurrentDate)
            } catch (e1: ParseException) {
                //                e1.printStackTrace();
                val format2 = SimpleDateFormat(yyyyMMddThhmmssSZ, Locale.ENGLISH)
                try {
                    newDate = format2.parse(strCurrentDate)
                } catch (e2: ParseException) {
                    //                    e2.printStackTrace();
                    val format3 = SimpleDateFormat(yyyyMMddThhmmssZ, Locale.ENGLISH)
                    try {
                        newDate = format3.parse(strCurrentDate)
                    } catch (e3: ParseException) {
                        //                        e3.printStackTrace();
                        return ""
                    }

                }

            }

        }

        format = SimpleDateFormat(ddmmyyyyFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }


    /**
     *
     * Checks if a date is today.
     *
     * @param date the date, not altered, not null.
     * @return true if the date is today.
     * @throws IllegalArgumentException if the date is `null`
     */
    fun isToday(date: Date?): Boolean {
        return isSameDay(date, Calendar.getInstance().time)
    }

    /**
     *
     * Checks if a calendar date is today.
     *
     * @param cal the calendar, not altered, not null
     * @return true if cal date is today
     * @throws IllegalArgumentException if the calendar is `null`
     */
    fun isToday(cal: Calendar): Boolean {
        return isSameDay(cal, Calendar.getInstance())
    }

    /**
     *
     * Checks if two dates are on the same day ignoring time.
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is `null`
     */
    fun isSameDay(date1: Date?, date2: Date?): Boolean {
        if (date1 == null || date2 == null) {
            throw IllegalArgumentException("The dates must not be null")
        }
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        return isSameDay(cal1, cal2)
    }

    /**
     *
     * Checks if two calendars represent the same day ignoring time.
     *
     * @param cal1 the first calendar, not altered, not null
     * @param cal2 the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is `null`
     */
    fun isSameDay(cal1: Calendar?, cal2: Calendar?): Boolean {
        if (cal1 == null || cal2 == null) {
            throw IllegalArgumentException("The dates must not be null")
        }
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    fun isOverdueDateTime(date_check: Date): Boolean {
        return date_check.before(Date())
    }

    fun isOverdueDateTimeFollowUp(dateCheck: Date): Boolean {
        if (isToday(dateCheck)) {
            val now = Calendar.getInstance()
            val from = 1700
            val to = 2359
            val t = now.get(Calendar.HOUR_OF_DAY) * 100 + now.get(Calendar.MINUTE)
            return to > from && t >= from && t <= to || to < from && (t >= from || t <= to)
        } else {
            return false
        }
    }

    fun isDateInCurrentWeek(date: Date): Boolean {
        val currentCalendar = Calendar.getInstance()
        val week = currentCalendar.get(Calendar.WEEK_OF_YEAR)
        val year = currentCalendar.get(Calendar.YEAR)
        val targetCalendar = Calendar.getInstance()
        targetCalendar.time = date
        val targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR)
        val targetYear = targetCalendar.get(Calendar.YEAR)
        return week == targetWeek && year == targetYear
    }

    fun compareDates(date1: Date, date2: Date): Boolean {
        return date1.before(date2)
    }

    fun getDateFromString(strCurrentDate: String): Date? {
        val format = SimpleDateFormat(eeeddMMMyyyyHHmmaFormat, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return newDate
    }

    fun formatDateToAPI(strCurrentDate: String): String {
        var format = SimpleDateFormat(eeeddMMMHHmmaFormat_withComma, Locale.ENGLISH) // yyyyMMdd hh:mm
        format.timeZone = TimeZone.getTimeZone("UTC")
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            return ""
        }

        format = SimpleDateFormat(yyyyMMddThhmmssSZ, Locale.ENGLISH)
        format.timeZone = TimeZone.getDefault()
        return format.format(newDate)
    }

    fun formatDateAppointmentToAPI(strCurrentDate: String): String {
        var format = SimpleDateFormat(eeeddMMMyyyyHHmmaFormat, Locale.ENGLISH) // yyyyMMdd hh:mm
        var newDate: Date? = null
        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            return ""
        }

        format = SimpleDateFormat(yyyyMMddThhmmssSSSZ, Locale.ENGLISH)
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(newDate)
    }

    private fun getUTCCustomFormat(format: String): SimpleDateFormat {
        val customFormat = SimpleDateFormat(format, Locale.ENGLISH)
        customFormat.timeZone = TimeZone.getTimeZone("UTC")

        return customFormat
    }

    fun getFormatDay24HoursTime(strCurrentDate: String): Long {
        var format = getUTCCustomFormat(yyyyMMddThhmmssZ)
        var newDate: Date? = null

        try {
            newDate = format.parse(strCurrentDate)
        } catch (e: ParseException) {
            format = getUTCCustomFormat(yyyyMMddThhmmssSZ) // yyyyMMdd hh:mm

            try {
                newDate = format.parse(strCurrentDate)
            } catch (e1: ParseException) {
                format = getUTCCustomFormat(yyyyMMddThhmmssSSZ) // yyyyMMdd hh:mm

                try {
                    newDate = format.parse(strCurrentDate)
                } catch (e1: ParseException) {
                    format = getUTCCustomFormat(yyyyMMddThhmmssSSSZ) // yyyyMMdd hh:mm

                    try {
                        newDate = format.parse(strCurrentDate)
                    } catch (_: ParseException) {
                    }
                }
            }
        }

        return newDate?.time.orElse(0)
    }
}

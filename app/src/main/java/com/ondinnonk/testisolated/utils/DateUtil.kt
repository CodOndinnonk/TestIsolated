package com.ondinnonk.testisolated.utils

import java.text.SimpleDateFormat
import java.util.*


object DateUtil {

    const val dd_MMMM_yyyy_HH_MM = "dd-MMMM-yyyy HH:mm"

    /**
     * Return date in specified format.
     * @param milliSeconds Date in milliseconds
     * @param dateFormat Date format
     * @return String representing date in specified format
     */
    fun getDate(
        milliSeconds: Long,
        dateFormat: String
    ): String? { // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

}
package com.example.etpp

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Helper {
    fun addLeadingZero(number: String): String {
        var number = number
        if (number.length < 2) {
            number = "0$number"
        }
        return number
    }

    //    public static String convertDateFormat(String yyyyMMDD){
    //        DateTimeFormatter yMDFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    //        DateTimeFormatter dMYFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY", Locale.ENGLISH);
    //        LocalDate date = LocalDate.parse(yyyyMMDD, yMDFormat);
    //        return  date.format(dMYFormat);
    //    }
    fun convertDateFormatOlder(yyyyMMDD: String?): String? {
        val yMDFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = yMDFormat.parse(yyyyMMDD)
            val dMYFormat = SimpleDateFormat("dd/MM/yyyy")
            return dMYFormat.format(date)
        }
        catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun convertDateToString(date: Date?): String {
        val dMYFormat = SimpleDateFormat("dd/MM/yyyy")
        return dMYFormat.format(date)
    }

    fun convertDatetoDBString(date: Date?): String {
        val yMDFormat = SimpleDateFormat("yyyy-MM-dd")
        return yMDFormat.format(date)
    }

    fun getCurrentDate(): Date {
        val today = Calendar.getInstance()
        return today.time
    }

    fun dateWeekAgo(): Date {
        val today = Calendar.getInstance()
        today.add(Calendar.DATE, -6)
        return today.time

    }
}

package com.kelci.familynote.Utilities

import android.util.Log
import java.util.*

class CommonUtil {
    companion object {
        fun getTodayDate() : String {
            var date = ""
            val calendarF = Calendar.getInstance()
            val day = calendarF.get(Calendar.DAY_OF_MONTH)
            val year = calendarF.get(Calendar.YEAR)
            val month = calendarF.get(Calendar.MONTH)

            when (month < 9) {
                true -> date = "" + year + "-0" + (month + 1) + "-" + day
                false -> date = "" + year + (month + 1) + "-" + day
            }
            Log.i("today", "Today's date is: " + date)
            return date
        }
    }
}
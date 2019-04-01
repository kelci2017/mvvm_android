package com.kelci.familynote.utilities

import android.content.Context
import android.util.Log
import com.kelci.familynote.FamilyNoteApplication
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

        fun generateRandom(): Int {
            val random = Random()
            return random.nextInt(9999 - 1000) + 1000
        }

        fun getStringSharePreference(preferences_tag: String?, key: String?, defaultValue: String): String? {
            if (preferences_tag == null || preferences_tag.isEmpty()) return defaultValue
            if (key == null || key.isEmpty()) return defaultValue

            val preferences = FamilyNoteApplication.familyNoteApplication?.getSharedPreferences(preferences_tag, Context.MODE_PRIVATE)
            return preferences?.getString(key, defaultValue)
        }

        @Synchronized
        fun saveStringSharePreference(preferences_tag: String?, key: String?, value: String) {
            if (preferences_tag == null || preferences_tag.isEmpty()) return
            if (key == null || key.isEmpty()) return

            val preferences = FamilyNoteApplication.familyNoteApplication?.getSharedPreferences(preferences_tag, Context.MODE_PRIVATE)
            val editor = preferences?.edit()
            editor?.putString(key, value)
            editor?.commit()
        }
    }
}
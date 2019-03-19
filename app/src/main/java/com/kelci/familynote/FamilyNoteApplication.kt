package com.kelci.familynote

import android.app.Application
import android.content.Context
import net.grandcentrix.tray.AppPreferences
import restClient.VolleyService

class FamilyNoteApplication : Application() {

    private var appPreferences: AppPreferences? = null

    companion object {
        //var context: Context? = null
        var familyNoteApplication: FamilyNoteApplication? = null
    }



    override fun onCreate() {
        super.onCreate()

        //FamilyNoteApplication.context = applicationContext
        familyNoteApplication = this
        appPreferences = AppPreferences(applicationContext)
        VolleyService.setRequestQueue(applicationContext)
    }

    fun putKeyValue(key: String?, value: String?) {
        if (appPreferences == null) return
        if (key == null || value == null) return
        appPreferences?.put(key, value)
    }

    fun getKeyValue(key: String): String? {
        return appPreferences?.getString(key, null)
    }
}
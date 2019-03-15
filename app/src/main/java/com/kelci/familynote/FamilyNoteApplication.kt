package com.kelci.familynote

import android.app.Application
import android.content.Context
import net.grandcentrix.tray.AppPreferences

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
    }

    fun putKeyValue(key: String?, value: String?) {
        if (appPreferences == null) return
        if (key == null || value == null) return
        appPreferences?.put(key, value)
    }

    /**
     * Added by Jason on 05/07/2017. Get value by keyword from cache library.
     * Will return null if not exist.
     * @param key
     * @return
     */
    fun getKeyValue(key: String): String? {
        return appPreferences?.getString(key, null)
    }
}
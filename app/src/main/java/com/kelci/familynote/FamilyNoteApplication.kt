package com.kelci.familynote

import android.app.Application
import net.grandcentrix.tray.AppPreferences
import restclient.VolleyService
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.restService.rest_client.ServiceUtil
import com.kelci.familynote.model.restService.fcm.RegistrationIntentService
import com.kelci.familynote.utilities.CommonUtil
import restclient.RestHandler
import restclient.RestResult


class FamilyNoteApplication : Application() {

    private var appPreferences: AppPreferences? = null
    private var sharedPref : SharedPreferences? = null
    private val gson = Gson()


    companion object {
        var familyNoteApplication: FamilyNoteApplication? = null
        val FAMILYNOTE_TAG = "FamilyNoteApp"
        val app_unique_id = "app_unique_id"
    }

    override fun onCreate() {
        super.onCreate()

        //FamilyNoteApplication.context = applicationContext
        familyNoteApplication = this
        appPreferences = AppPreferences(applicationContext)
        VolleyService.setRequestQueue(applicationContext)
        if (getKeyValue(resources.getString(R.string.sessionid)) != null && getKeyValue(resources.getString(R.string.token)) != null) {
            getFamilyMemberList()
        }
        FirebaseApp.initializeApp(this)
    }

    fun putKeyValue(key: String?, value: String?) {
        if (appPreferences == null) return
        if (key == null) return
        appPreferences?.put(key, value)
    }

    fun putKeyArralylist(key: String?, value : ArrayList<String>?) {
        sharedPref  = getSharedPreferences("FamilyNote", MODE_PRIVATE)
        val jsonText = gson.toJson(value)
        Log.i("FamilyNoteApplication", "aaaaaaaaaaaaaa" + jsonText)

        val editor = sharedPref?.edit()
        editor?.putString(key, jsonText)
        editor?.apply()
    }

    fun getKeyValue(key: String): String? {
        return appPreferences?.getString(key, null)
    }

    fun getKeyArraylist(key: String): ArrayList<String>? {

        val type = object : TypeToken<ArrayList<String>>() {

        }.type
        return gson.fromJson(sharedPref?.getString(resources.getString(R.string.member_list), null), type)
    }

    private var mCurrentActivity: Activity? = null
    fun getCurrentActivity(): Activity? {
        return mCurrentActivity
    }

    fun setCurrentActivity(mCurrentActivity: Activity) {
        this.mCurrentActivity = mCurrentActivity
    }

    fun getFamilyMemberList() {
        var restHandler : RestHandler<BaseResult>? = null

        restHandler as RestHandler<Any>?


        ServiceUtil.getFamilyMemberList(null,null,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : BaseResult? = result?.resultObject as? BaseResult

                if (baseResult != null && baseResult.isSuccess()) {
                    val familyMemberList : ArrayList<String>? = baseResult.resultDesc as ArrayList<String>
                    if (familyMemberList != null) {
                        putKeyArralylist(resources.getString(R.string.member_list), familyMemberList)
                    }
                }
            }
        }, false)
    }

    fun initPushNotifications() {
            val intent = Intent(this, RegistrationIntentService::class.java)
            startService(intent)
    }

    fun generateAppUniqueId(): String {
        var appUniqueId = CommonUtil.getStringSharePreference(FAMILYNOTE_TAG, app_unique_id, "")
        if (appUniqueId == null || appUniqueId == "") {
            appUniqueId = getAppPackageName() + "_" + hashCode()
            CommonUtil.saveStringSharePreference(FAMILYNOTE_TAG, app_unique_id, appUniqueId)
        }
        return appUniqueId
    }

    fun getAppPackageName(): String {
        return BuildConfig.APPLICATION_ID
    }
}
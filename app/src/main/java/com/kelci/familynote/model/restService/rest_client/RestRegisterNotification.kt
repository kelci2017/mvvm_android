package com.kelci.familynote.model.restService.rest_client

import android.util.Log
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.dataStructure.UserPostBody
import com.kelci.familynote.utilities.CommonCodes
import com.kelci.familynote.utilities.ServerResponseChecker
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestRegisterNotification  : VolleyService() {

    override fun parseResult(result: JSONObject?): RestResult<BaseResult> {
        val errorCode = ServerResponseChecker.onCheck(result.toString())
        if (errorCode != CommonCodes.NO_ERROR) {
            return RestResult(CommonCodes.NETWORK_ERROR, errorCode)
        }
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        return RestResult(baseResult)
    }

    override fun getUrl(): String {

        val registerNotificationString = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.notification_register)
        val serverURL = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.server_url)
        val sessionid = FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.sessionid))

        Log.i(javaClass.name, "the url is: " + String.format(registerNotificationString, serverURL, sessionid))

        return String.format(registerNotificationString, serverURL, sessionid)
    }

    class RegisterNotificationPostBody
    constructor(var token : String = "", var deviceID : String? = null, var deviceType : String = "Android")

    override fun getRequestType(): String {
        return "POST"
    }

    override fun generatePostBody(): String {
        val token = getParameter("token") as String
        val deviceid = FamilyNoteApplication.familyNoteApplication?.generateAppUniqueId()
        val deviceType = "Android"

        val registerBody = RegisterNotificationPostBody(token, deviceid, deviceType)

        return toJson(registerBody)
    }
}
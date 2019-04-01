package com.kelci.familynote.model.restService.rest_client

import android.util.Log
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.FamilyNoteApplication.Companion.FAMILYNOTE_TAG
import com.kelci.familynote.FamilyNoteApplication.Companion.app_unique_id
import com.kelci.familynote.R
import com.kelci.familynote.utilities.CommonUtil
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.dataStructure.NoteBody
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestSubmitNote : VolleyService() {

    var date : String = ""
    var sender : String = ""
    var receiver : String = ""
    var noteBody : String = ""

    override fun parseResult(result: JSONObject?): RestResult<BaseResult> {
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        return RestResult(baseResult)
    }

    override fun getUrl(): String {

        val submitNoteString = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.submit_note)
        val serverURL = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.server_url)
        val sessionid = FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.sessionid))
        val deviceid = CommonUtil.getStringSharePreference(FAMILYNOTE_TAG, app_unique_id, "")

        Log.i(javaClass.name, "the submit note url is: " + String.format(submitNoteString, serverURL, sessionid, deviceid))

        return String.format(submitNoteString, serverURL, sessionid, deviceid)
    }

    override fun initialize(): RestResult<Any> {
        sender = getParameter(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.sender)) as String
        receiver = getParameter(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.receiver)) as String
        noteBody = getParameter(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.note_body)) as String

        date = CommonUtil.getTodayDate()

        return RestResult()
    }

    override fun needAuthorization(): Boolean {
        return true
    }

    override fun getRequestType(): String {
        return "POST"
    }

    override fun getToken(): String? {
        return FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.token))
    }

    override fun generatePostBody(): String {
        var postBody : NoteBody? = null
        postBody = NoteBody(sender, receiver, noteBody, date, FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.userID)))

        return toJson(postBody)
    }
}
package com.kelci.familynote.model.restService

import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.dataStructure.Note
import org.json.JSONObject
import restClient.RestResult
import restClient.VolleyService
import java.util.*

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

        return String.format(submitNoteString, serverURL, sessionid)
    }

    override fun initialize(): RestResult<Any> {
        sender = getParameter(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.sender)) as String
        receiver = getParameter(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.receiver)) as String
        noteBody = getParameter(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.note_body)) as String

        val calendarF = Calendar.getInstance()
        val day = calendarF.get(Calendar.DAY_OF_MONTH)
        val year = calendarF.get(Calendar.YEAR)
        val month = calendarF.get(Calendar.MONTH)

        when (month < 9) {
            true -> date = "" + year + "-0" + (month + 1) + "-" + day
            false -> date = "" + year + (month + 1) + "-" + day
        }

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
        var postBody : Note? = null
        postBody = Note(sender, receiver, noteBody, date, FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.userID)))

        return toJson(postBody)
    }
}
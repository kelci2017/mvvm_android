package com.kelci.familynote.model.restService

import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.utilities.CommonUtil
import com.kelci.familynote.model.dataStructure.BaseResult
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestFilterNote : VolleyService() {
    var date : String = CommonUtil.getTodayDate()
    var sender : String = "All"
    var receiver : String = "All"

    override fun parseResult(result: JSONObject?): RestResult<BaseResult> {
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        return RestResult(baseResult)
    }

    override fun getUrl(): String {

        val searchNoteString = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.note_filter)
        val serverURL = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.server_url)
        val sessionid = FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.sessionid))

        return String.format(searchNoteString, serverURL, sender, receiver, date, sessionid)
    }

    override fun needAuthorization(): Boolean {
        return true
    }

    override fun initialize(): RestResult<Any> {
        date = getParameter("date") as String
        sender = getParameter("from") as String
        receiver = getParameter("to") as String

        return RestResult()
    }

    override fun getToken(): String? {
        return FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.token))
    }
}
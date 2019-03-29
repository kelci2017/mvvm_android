package com.kelci.familynote.model.restService

import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestLogout : VolleyService() {

    override fun parseResult(result: JSONObject?): RestResult<BaseResult> {
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        return RestResult(baseResult)
    }

    override fun getUrl(): String {

        val logoutString = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.logout)
        val serverURL = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.server_url)
        val sessionid = FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.sessionid))

        return String.format(logoutString, serverURL, sessionid)
    }

    override fun needAuthorization(): Boolean {
        return true
    }

    override fun getToken(): String? {
        return FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.token))
    }
}
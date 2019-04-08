package com.kelci.familynote.model.restService.rest_client

import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.utilities.CommonCodes
import com.kelci.familynote.utilities.ServerResponseChecker
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestGetFamilyMemberList : VolleyService() {

    override fun parseResult(result: JSONObject?): RestResult<BaseResult> {
        val errorCode = ServerResponseChecker.onCheck(result.toString())
        if (errorCode != CommonCodes.NO_ERROR) {
            return RestResult(CommonCodes.NETWORK_ERROR, errorCode)
        }
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        return RestResult(baseResult)
    }

    override fun getUrl(): String {

        val logoutString = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.get_family_members)
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
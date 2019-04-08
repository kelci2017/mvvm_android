package com.kelci.familynote.model.restService.rest_client

import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.utilities.CommonCodes
import com.kelci.familynote.utilities.ServerResponseChecker
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestGetToken : VolleyService() {

    override fun parseResult(result: JSONObject?): RestResult<TokenSessionRestResult> {
        val errorCode = ServerResponseChecker.onCheck(result.toString())
        if (errorCode != CommonCodes.NO_ERROR) {
            return RestResult(CommonCodes.NETWORK_ERROR, errorCode)
        }
        val tokenSessionRestResult = fromJson<TokenSessionRestResult>(result.toString(), TokenSessionRestResult::class.java)
        return RestResult(tokenSessionRestResult)
    }

    override fun getUrl(): String {
        return String.format(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.get_token), FamilyNoteApplication.familyNoteApplication!!.getString(R.string.server_url), FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.sessionid)))
    }
}
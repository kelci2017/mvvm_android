package com.kelci.familynote.model.restService

import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import org.json.JSONObject
import restClient.RestResult
import restClient.VolleyService

class RestGetToken : VolleyService() {

    override fun parseResult(result: JSONObject?): RestResult<TokenSessionRestResult> {
        val tokenSessionRestResult = fromJson<TokenSessionRestResult>(result.toString(), TokenSessionRestResult::class.java)
        return RestResult(tokenSessionRestResult)
    }

    override fun getUrl(): String {
        return String.format(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.get_token), FamilyNoteApplication.familyNoteApplication!!.getString(R.string.server_url), FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.sessionid)))
    }
}
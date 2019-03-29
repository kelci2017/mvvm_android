package com.kelci.familynote.model.restService.rest_client

import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.model.dataStructure.UserPostBody
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestLogin : VolleyService() {

    var email : String = ""
    var password : String = ""

    override fun parseResult(result: JSONObject?): RestResult<TokenSessionRestResult> {
        val tokenSessionRestResult = fromJson<TokenSessionRestResult>(result.toString(), TokenSessionRestResult::class.java)
        return RestResult(tokenSessionRestResult)
    }

    override fun getUrl(): String {

        return String.format(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.login), FamilyNoteApplication.familyNoteApplication!!.getString(R.string.server_url))
    }

    override fun initialize(): RestResult<Any> {
        email = getParameter(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.user_name)) as String
        password = getParameter(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.user_password)) as String

        return RestResult()
    }

    override fun getRequestType(): String {
        return "POST"
    }

    override fun generatePostBody(): String {
        var postBody : UserPostBody? = null
        postBody = UserPostBody(email, password)

        return toJson(postBody)
    }
}
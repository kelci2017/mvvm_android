package com.kelci.familynote.model.restService

import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.UserPostBody
import restClient.RestResult
import restClient.VolleyService

class RestRegister : VolleyService(){
    var email : String = ""
    var password : String = ""

    override fun parseResult(result: String?): RestResult<*> {
        return RestResult<Any>()

    }

    override fun getUrl(): String {
        return String.format(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.register))
    }

    override fun initialize(): RestResult<Any> {
        email = getParameter("email") as String
        password = getParameter("password") as String

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
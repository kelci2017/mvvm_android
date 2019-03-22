package com.kelci.familynote.model.restService

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import org.json.JSONObject
import restClient.RestResult
import restClient.VolleyService

class RestAddFamilyMember : VolleyService() {

    var familyMemberList : ArrayList<String>? = null

    override fun parseResult(result: JSONObject?): RestResult<BaseResult> {
        val baseResult = fromJson<BaseResult>(result.toString(), BaseResult::class.java)
        return RestResult(baseResult)
    }

    override fun getUrl(): String {

        val familyMemberString = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.add_family_member)
        val serverURL = FamilyNoteApplication.familyNoteApplication!!.getString(R.string.server_url)
        val sessionid = FamilyNoteApplication.familyNoteApplication!!.getKeyValue(FamilyNoteApplication.familyNoteApplication!!.getString(R.string.sessionid))

        return String.format(familyMemberString, serverURL, sessionid)
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

    override fun initialize(): RestResult<Any> {
        val famimyMemberListString = getParameter(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) as String
        val type = object : TypeToken<ArrayList<String>>() {

        }.type
        val gson = Gson()

        familyMemberList = gson.fromJson(famimyMemberListString, type)

        return RestResult()
    }

    override fun generatePostBody(): String {
        var postBody : ArrayList<String>? = null
        if (FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) != null) {
            val savedList = FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) as ArrayList<String>
            familyMemberList?.addAll(savedList)
        }

        FamilyNoteApplication.familyNoteApplication?.putKeyArralylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list), familyMemberList)

        postBody = familyMemberList

        return toJson(postBody)
    }
}
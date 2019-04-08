package com.kelci.familynote.model.restService.rest_client

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.utilities.CommonCodes
import com.kelci.familynote.utilities.ServerResponseChecker
import org.json.JSONObject
import restclient.RestResult
import restclient.VolleyService

class RestAddFamilyMember : VolleyService() {

    private var familyMemberList = ArrayList<String>()

    override fun parseResult(result: JSONObject?): RestResult<BaseResult> {
        val errorCode = ServerResponseChecker.onCheck(result.toString())
        if (errorCode != CommonCodes.NO_ERROR) {
            return RestResult(CommonCodes.NETWORK_ERROR, errorCode)
        }
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

        //val savedList = FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) as ArrayList<String>

        if (familyMemberList != null) {

            //familyMemberList.addAll(savedList)
            FamilyNoteApplication.familyNoteApplication?.putKeyArralylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list), familyMemberList)
        }
        val userID = FamilyNoteApplication.familyNoteApplication?.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.userID))
        val postBody = FamilyMemberPostBody(familyMemberList, userID)

        return toJson(postBody)
    }

    class FamilyMemberPostBody(familyMembers : ArrayList<String>, userID : String?) {

        private var familyMembers = ArrayList<String>()
        private var userID : String? = null

        init {
            this.familyMembers = familyMembers
            this.userID = userID
        }
    }
}
package com.kelci.familynote.viewmodel

import com.google.gson.Gson
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.restService.ServiceUtil
import com.kelci.familynote.viewmodel.base.BaseViewModel
import com.kelci.familynote.viewmodel.base.MultipleObserverLiveDate
import restclient.RestHandler
import restclient.RestParms
import restclient.RestResult
import restclient.RestTag

class AddFamilyMemberViewModel  : BaseViewModel() {

    var addFamilyMemberResult = MultipleObserverLiveDate<BaseResult>()

    var familyMemberList : ArrayList<String>? = null

    fun addFamilyMmeber(familyMemberList : ArrayList<String>) {

        this.familyMemberList = familyMemberList

        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.token)) != null) {
            callAddFamilyMember(familyMemberList)
        } else {
            getToken()
        }
    }

    override fun callNext() {

        callAddFamilyMember(familyMemberList)

    }
    private fun callAddFamilyMember(familyMemberList : ArrayList<String>?) {

        var restHandler : RestHandler<BaseResult>? = null
        restHandler as RestHandler<Any>?

        val restTag = RestTag()
        restTag.tag = "AddFamilyMembers"

        var restParams : RestParms = RestParms()
        val gson = Gson()
        val familyMemberListString = gson.toJson(familyMemberList)

        restParams.setParams(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list), familyMemberListString)

        ServiceUtil.addFamilyMember(restTag,restParams,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : BaseResult? = result?.resultObject as? BaseResult

                if (baseResult != null) {
                    addFamilyMemberResult.value = baseResult
                }
            }
        }, false)
    }
}
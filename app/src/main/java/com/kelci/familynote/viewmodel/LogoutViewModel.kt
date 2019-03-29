package com.kelci.familynote.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.restService.rest_client.ServiceUtil
import com.kelci.familynote.viewmodel.base.BaseViewModel
import restclient.RestHandler
import restclient.RestResult
import restclient.RestTag

class LogoutViewModel : BaseViewModel() {

    var logoutResult = MutableLiveData<BaseResult>()

    fun logout() {

        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.token)) != null) {
            callLogout()
        } else {
             getToken()
        }
    }

    override fun callNext() {

        callLogout()

    }
    private fun callLogout() {

        var restHandler : RestHandler<BaseResult>? = null

        restHandler as RestHandler<Any>?

        var restTag = RestTag()
        restTag.tag = "Logout"

        ServiceUtil.logout(restTag,null,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val baseResult : BaseResult? = result?.resultObject as? BaseResult

                if (baseResult != null) {
                    logoutResult.value = baseResult
                }
            }
        }, false)
    }
}
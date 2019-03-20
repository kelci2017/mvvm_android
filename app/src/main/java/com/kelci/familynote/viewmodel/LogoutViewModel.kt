package com.kelci.familynote.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.BaseResult
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.model.restService.ServiceUtil
import com.kelci.familynote.viewmodel.base.BaseViewModel
import restClient.RestHandler
import restClient.RestResult
import restClient.RestTag

class LogoutViewModel : BaseViewModel() {

    var logoutResult = MutableLiveData<BaseResult>()

    fun logout() {

        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.sessionid)) != null) {
            callLogout()
        } else {
//             if (getToken()) {
//                 callLogout()
//             }
            ServiceUtil.getToken(null,null,object : RestHandler<Any>(){
                override fun onReturn(result: RestResult<Any>?) {

                    val tokenSessionRestResult : TokenSessionRestResult? = result?.resultObject as? TokenSessionRestResult

                    if (tokenSessionRestResult != null && tokenSessionRestResult.isSuccess()) {
                        FamilyNoteApplication.familyNoteApplication?.putKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.token), tokenSessionRestResult.getToken())
                        callLogout()
                    }
                }
            }, false)
        }
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
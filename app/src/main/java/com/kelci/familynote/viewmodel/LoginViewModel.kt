package com.kelci.familynote.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.model.restService.ServiceUtil
import com.kelci.familynote.viewmodel.base.BaseViewModel
import restclient.RestHandler
import restclient.RestParms
import restclient.RestResult
import restclient.RestTag

class LoginViewModel : BaseViewModel() {

    var userData = MutableLiveData<TokenSessionRestResult>()

    fun login(email : String, password : String) {
        var restParams : RestParms = RestParms()
        restParams.setParams(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.user_name), email, FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.user_password), password)

        var restHandler : RestHandler<TokenSessionRestResult>? = null

        restHandler as RestHandler<Any>?

        val restTag = RestTag()
        restTag.tag = "Login"

        ServiceUtil.login(restTag,restParams,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                    val tokenSessionRestResult : TokenSessionRestResult? = result?.resultObject as? TokenSessionRestResult

                    if (tokenSessionRestResult != null) {
                        //val user = User(tokenSessionRestResult.getSessionid(), tokenSessionRestResult.getToken(), tokenSessionRestResult.getUserID())
                        userData.value = tokenSessionRestResult
                    }
            }
        }, false)
    }
}
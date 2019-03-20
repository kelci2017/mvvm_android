package com.kelci.familynote.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.model.restService.ServiceUtil
import com.kelci.familynote.viewmodel.base.BaseViewModel
import restClient.RestHandler
import restClient.RestParms
import restClient.RestResult
import restClient.RestTag

class LoginViewModel : BaseViewModel() {

    var userData = MutableLiveData<TokenSessionRestResult>()

    fun login(email : String, password : String) {
        var restParams : RestParms = RestParms()
        restParams.setParams("email", email, "password", password)

        var restHandler : RestHandler<TokenSessionRestResult>? = null

        restHandler as RestHandler<Any>?

        var restTag = RestTag()
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
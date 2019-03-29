package com.kelci.familynote.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.model.restService.rest_client.ServiceUtil
import com.kelci.familynote.viewmodel.base.BaseViewModel
import restclient.RestHandler
import restclient.RestParms
import restclient.RestResult
import restclient.RestTag

class RegisterViewModel : BaseViewModel() {

    var userData = MutableLiveData<TokenSessionRestResult>()

    fun register(email : String, password : String) {
        var restParams : RestParms = RestParms()
        restParams.setParams("email", email, "password", password)

        var restHandler : RestHandler<TokenSessionRestResult>? = null

        restHandler as RestHandler<Any>?

        var restTag = RestTag()
        restTag.tag = "Register"

        ServiceUtil.register(restTag,restParams,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val tokenSessionRestResult : TokenSessionRestResult? = result?.resultObject as? TokenSessionRestResult

                if (tokenSessionRestResult != null) {
                    //val user = User(tokenSessionRestResult.getSessionid(), tokenSessionRestResult.getToken(), tokenSessionRestResult.getUserID())
                    userData.value = tokenSessionRestResult
                    return
                }
            }
        }, false)
    }
}
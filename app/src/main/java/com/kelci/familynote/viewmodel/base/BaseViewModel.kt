package com.kelci.familynote.viewmodel.base

import android.arch.lifecycle.ViewModel
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.model.restService.ServiceUtil
import restClient.RestHandler
import restClient.RestResult

open class BaseViewModel : ViewModel() {

    fun getToken() : Boolean {
        var tokenFetched = false
        ServiceUtil.getToken(null,null,object : RestHandler<Any>(){
            override fun onReturn(result: RestResult<Any>?) {

                val tokenSessionRestResult : TokenSessionRestResult? = result?.resultObject as? TokenSessionRestResult

                if (tokenSessionRestResult != null && tokenSessionRestResult.isSuccess()) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.sessionid), tokenSessionRestResult.getSessionid())
                    tokenFetched = true
                }
            }
        }, false)

        return tokenFetched
    }
}
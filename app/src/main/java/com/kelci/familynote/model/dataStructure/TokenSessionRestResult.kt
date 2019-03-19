package com.kelci.familynote.model.dataStructure

class TokenSessionRestResult(resultCode : Int, resultDesc : Any, token : String?, sessionID : String?, userID : String?) : BaseResult(resultCode, resultDesc)  {

    private var token : String? = null
    private var sessionID : String? = null
    private var userID : String? = null

    init {
        this.sessionID = sessionID
        this.token = token
        this.userID = userID
    }

    fun getToken() : String? {
        return token
    }

    fun getSessionid() : String? {
        return sessionID
    }

    fun getUserID() : String? {
        return userID
    }
}
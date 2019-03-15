package com.kelci.familynote.model.dataStructure

class TokenSessionRestResult(token : String, sessionid : String, userID : String)  {

    private var token : String? = null
    private var sessionid : String? = null
    private var userID : String? = null

    init {
        this.sessionid = sessionid
        this.token = token
        this.userID = userID
    }

    fun getToken() : String? {
        return token
    }

    fun getSessionid() : String? {
        return sessionid
    }

    fun getUserID() : String? {
        return userID
    }
}
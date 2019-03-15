package com.kelci.familynote.model.dataStructure

class User(sessionid : String, token : String, userID : String) {

    private var sessionid : String = ""
    private var token : String = ""
    private var userID : String = ""

    init {
        this.sessionid = sessionid
        this.token = token
        this.userID = userID
    }

    fun getSessionid() : String {
        return sessionid
    }

    fun getUserID() : String {
        return userID
    }

    fun getToken() : String {
        return token
    }
}
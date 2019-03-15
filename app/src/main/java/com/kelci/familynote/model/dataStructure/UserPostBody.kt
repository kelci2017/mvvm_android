package com.kelci.familynote.model.dataStructure

class UserPostBody(email : String, password : String) {

    private var email : String = ""
    private var password : String = ""

    init {
        this.email = email
        this.password = password
    }

    fun getEmail() : String {
        return email
    }
}
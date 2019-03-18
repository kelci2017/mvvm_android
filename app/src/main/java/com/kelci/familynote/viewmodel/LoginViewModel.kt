package com.kelci.familynote.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import com.kelci.familynote.model.dataStructure.User

class LoginViewModel : ViewModel {

    var userDate: LiveData<User>? = null

    constructor()

    fun login(email : String, password : String) {
        // depending on the action, do necessary business logic calls and update the
        // userLiveData.
    }
}
package com.kelci.familynote.view.initial

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.view.base.RootActivity
import com.kelci.familynote.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : RootActivity() {

    private lateinit var loginModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        register_button.setOnClickListener{ view ->
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        observeViewModel(loginModel)

        autoFillEmailPassword()

        login_button.setOnClickListener { view ->

            if (validEmailPassword(useremail as EditText, password_text as EditText)) {
                if (FamilyNoteApplication.familyNoteApplication!!.isInternetAvailable()) {
                    loginModel.login(useremail.text.toString(), password_text.text.toString())
                    showProgressDialog("Loading...")
                } else {
                    showNetworkError()
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.sessionid)) != null) {
            showMainActivity(this@LoginActivity as RootActivity)
        }
    }

    private fun observeViewModel(viewModel : LoginViewModel) {

        viewModel.userData.observe(this, object : Observer<TokenSessionRestResult>{
            override fun onChanged(@Nullable tokenSessionRestResult: TokenSessionRestResult?) {
                dismissProgressDialog()
                if (tokenSessionRestResult!!.isSuccess()) {
                    //save the username and password for autologin
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.user_name), useremail.text.toString())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.user_password), password_text.text.toString())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), tokenSessionRestResult.getToken())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.userID), tokenSessionRestResult.getUserID())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.sessionid), tokenSessionRestResult.getSessionid())

                    Log.i(javaClass.name, "JWT token is: " + tokenSessionRestResult.getToken())
                    Log.i(javaClass.name, "sessionid is: " + tokenSessionRestResult.getSessionid())

                    if (FamilyNoteApplication.familyNoteApplication?.getKeyArraylist(FamilyNoteApplication.familyNoteApplication?.resources!!.getString(R.string.member_list)) == null) {
                        FamilyNoteApplication.familyNoteApplication?.getFamilyMemberList()
                    }

                    showMainActivity(this@LoginActivity as RootActivity)
                } else {
                    errorHandler(tokenSessionRestResult.resultDesc.toString(), "Login failed!")
                }
            }
        })
    }

    private fun autoFillEmailPassword() {
        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_name)) != null) {
            useremail.setText(FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_name)))
        }
        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_password)) != null) {
            password_text.setText(FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_password)))
        }
    }
}
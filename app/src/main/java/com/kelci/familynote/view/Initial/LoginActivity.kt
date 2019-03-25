package com.kelci.familynote.view.Initial

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.view.Base.RootActivity
import com.kelci.familynote.viewmodel.LoginViewModel
import java.util.*
import java.util.regex.Pattern


class LoginActivity : RootActivity() {

    private var email: TextView? = null
    private var password: TextView? = null
    private var loginButton: Button? = null
    private var registerLink: Button? = null
    private lateinit var loginModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById<EditText>(R.id.useremail) as EditText
        password = findViewById<EditText>(R.id.password_text) as EditText
        loginButton = findViewById<Button>(R.id.login_button) as Button
        registerLink = findViewById<Button>(R.id.register_button) as Button

        registerLink?.setOnClickListener{ view ->
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        observeViewModel(loginModel)

        autoFillEmailPassword()

        loginButton?.setOnClickListener { view ->

            if (validEmailPassword(email as EditText, password as EditText)) {
                loginModel.login(email?.text.toString(), password?.text.toString())
                showProgressDialog("Loading...")
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
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.user_name), email?.text.toString())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.user_password), password?.text.toString())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), tokenSessionRestResult.getToken())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.userID), tokenSessionRestResult.getUserID())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.sessionid), tokenSessionRestResult.getSessionid())

                    FamilyNoteApplication.familyNoteApplication?.getFamilyMemberList()

                    showMainActivity(this@LoginActivity as RootActivity)
                } else {
                    errorHandler(tokenSessionRestResult.getResultDesc().toString(), "Login failed!")
                }
            }
        })
    }

    private fun autoFillEmailPassword() {
        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_name)) != null) {
            email?.text = FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_name))
        }
        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_password)) != null) {
            password?.text = FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_password))
        }
    }
}
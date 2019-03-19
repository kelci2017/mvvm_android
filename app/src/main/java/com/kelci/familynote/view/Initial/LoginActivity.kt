package com.kelci.familynote.view.Initial

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.view.Base.RootActivity
import com.kelci.familynote.viewmodel.LoginViewModel
import java.util.*
import java.util.regex.Pattern


class LoginActivity : RootActivity {

    private var email: TextView? = null
    private var password: TextView? = null
    private var loginButton: Button? = null
    private var registerLink: Button? = null
    private lateinit var loginModel: LoginViewModel

    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById<TextView>(R.id.useremail)
        password = findViewById<TextView>(R.id.password_text)
        loginButton = findViewById<Button>(R.id.login_button) as Button
        registerLink = findViewById<Button>(R.id.register_button) as Button

        registerLink?.setOnClickListener{ view ->
            val intent = Intent(this, RegisterActivity::class.java)

            startActivity(intent)

        }

        loginModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        observeViewModel(loginModel)
        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_name)) != null) {
            email?.text = FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_name))
        }
        if (FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_password)) != null) {
            password?.text = FamilyNoteApplication.familyNoteApplication?.getKeyValue(resources.getString(R.string.user_password))
        }
        loginButton?.setOnClickListener { view ->

            //first check the username and password is null and format
            if (email?.text == null) {
                showAlertBox("Please enter your email.", "Empty email!")
            }
            if (password?.text == null) {
                showAlertBox("Please enter your password.", "Empty password!")
            }
            if (!isEmailValid(email?.text.toString())) {
                showAlertBox("Please enter a valid email address.", "Wrong email format!")
            }

            //save the username and password for autologin
            FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.user_name), email?.text.toString())
            FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.user_password), password?.text.toString())

            loginModel.login(email?.text.toString(), password?.text.toString())
        }
    }

    private fun errorHandler(message: String) {
        dismissProgressDialog()
        showAlertBox("Log In Failed", message)
    }

    private fun observeViewModel(viewModel : LoginViewModel) {

        viewModel.userData?.observe(this, object : Observer<TokenSessionRestResult>{
            override fun onChanged(@Nullable tokenSessionRestResult: TokenSessionRestResult?) {
                if (tokenSessionRestResult!!.isSuccess()) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), tokenSessionRestResult.getToken())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.userID), tokenSessionRestResult.getUserID())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.sessionid), tokenSessionRestResult.getSessionid())

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    errorHandler(tokenSessionRestResult.getResultDesc().toString())
                }
            }
        })
    }
}
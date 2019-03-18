package com.kelci.familynote.view.Initial

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.view.Base.RootActivity
import java.util.*


class LoginActivity : RootActivity {

    var email: TextView? = null
    var password: TextView? = null
    var loginButton: Button? = null
    var registerLink: Button? = null

    constructor() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById<TextView>(R.id.useremail)
        password = findViewById<TextView>(R.id.password_text)
        loginButton = findViewById<Button>(R.id.login_button)
        registerLink = findViewById<Button>(R.id.register_button)

        registerLink?.setOnClickListener{ view ->
            val intent = Intent(this, RegisterActivity::class.java)

            startActivity(intent)

        }

        loginButton?.setOnClickListener { view ->

            //first check the username and password is null and format

            //save the username and password for autologin
            FamilyNoteApplication.familyNoteApplication?.putKeyValue("", email?.text.toString())
            FamilyNoteApplication.familyNoteApplication?.putKeyValue("", password?.text.toString())
            //trigger the viewmodel to call the webservice


        }
    }
}
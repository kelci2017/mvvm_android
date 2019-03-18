package com.kelci.familynote.view.Initial

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.kelci.familynote.R
import com.kelci.familynote.view.Base.RootActivity

class RegisterActivity : RootActivity {

    var email: TextView? = null
    var password: TextView? = null
    var reenterPassword : TextView? = null
    var registerButton: Button? = null

    constructor() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = findViewById<TextView>(R.id.email_address)
        password = findViewById<TextView>(R.id.enter_password)
        reenterPassword = findViewById<Button>(R.id.reenter_password)
        registerButton = findViewById<Button>(R.id.register)

        registerButton?.setOnClickListener(){ view ->

        }
    }
}
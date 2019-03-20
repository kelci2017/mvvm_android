package com.kelci.familynote.view.Initial

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.Nullable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.model.dataStructure.TokenSessionRestResult
import com.kelci.familynote.view.Base.RootActivity
import com.kelci.familynote.viewmodel.RegisterViewModel
import java.util.regex.Pattern

class RegisterActivity : RootActivity() {

    var email: TextView? = null
    var password: TextView? = null
    var reenterPassword : TextView? = null
    var registerButton: Button? = null
    private lateinit var registerModel: RegisterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = findViewById<EditText>(R.id.email_address)
        password = findViewById<EditText>(R.id.enter_password)
        reenterPassword = findViewById<Button>(R.id.reenter_password)
        registerButton = findViewById<Button>(R.id.register)

        registerModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        observeViewModel(registerModel)

        registerButton?.setOnClickListener{ view ->

            if (validEmailPassword(email as EditText, password as EditText)) {
                if (validPasswordFormat()) {
                    registerModel.register(email?.text.toString(), password?.text.toString())
                    showProgressDialog("Loading...")
                }
            }
        }
    }

    private fun observeViewModel(viewModel : RegisterViewModel) {

        viewModel.userData.observe(this, object : Observer<TokenSessionRestResult> {
            override fun onChanged(@Nullable tokenSessionRestResult: TokenSessionRestResult?) {
                dismissProgressDialog()
                if (tokenSessionRestResult!!.isSuccess()) {
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), tokenSessionRestResult.getToken())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.userID), tokenSessionRestResult.getUserID())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.sessionid), tokenSessionRestResult.getSessionid())

                    showMainActivity(this@RegisterActivity as RootActivity)
                } else {
                    errorHandler(tokenSessionRestResult.getResultDesc().toString(), "Register failed!")
                }
            }
        })
    }

    private fun validPasswordFormat() : Boolean {
        if (password!!.text.count() < 8) {
           showAlertBox("Please enter a password at least 8 characters.", "Password too short!")
            return false
        }

        val regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")

        if (regex.matcher(password!!.text.toString()).find()) {
            showAlertBox("Please do not include special characters in the password.", "No special characters!")
            return false
        }

        if (password?.text.toString() != (reenterPassword?.text.toString())) {
            showAlertBox("Please re-enter your password.", "Password is not the same!")
            return false
        }
        return true
    }
}
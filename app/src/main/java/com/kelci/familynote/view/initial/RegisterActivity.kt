package com.kelci.familynote.view.initial

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
import com.kelci.familynote.view.base.RootActivity
import com.kelci.familynote.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : RootActivity() {

    private lateinit var registerModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        observeViewModel(registerModel)

        register.setOnClickListener{ view ->

            if (validEmailPassword(email_address as EditText, enter_password as EditText)) {
                if (validPasswordFormat()) {
                    registerModel.register(email_address.text.toString(), enter_password.text.toString())
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
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.user_name), email_address.text.toString())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.user_password), enter_password.text.toString())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.token), tokenSessionRestResult.getToken())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.userID), tokenSessionRestResult.getUserID())
                    FamilyNoteApplication.familyNoteApplication?.putKeyValue(resources.getString(R.string.sessionid), tokenSessionRestResult.getSessionid())

                    showMainActivity(this@RegisterActivity as RootActivity)
                } else {
                    errorHandler(tokenSessionRestResult.resultDesc.toString(), "Register failed!")
                }
            }
        })
    }

    private fun validPasswordFormat() : Boolean {
        if (enter_password!!.text.count() < 8) {
           showAlertBox("Please enter a password at least 8 characters.", "Password too short!")
            return false
        }

        val regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")

        if (regex.matcher(enter_password!!.text.toString()).find()) {
            showAlertBox("Please do not include special characters in the password.", "No special characters!")
            return false
        }

        if (enter_password.text.toString() != (reenter_password.text.toString())) {
            showAlertBox("Please re-enter your password.", "Password is not the same!")
            return false
        }
        return true
    }
}
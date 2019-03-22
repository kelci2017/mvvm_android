package com.kelci.familynote.view.Base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import com.kelci.familynote.view.Initial.MainActivity
import java.util.regex.Pattern
import android.widget.LinearLayout
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.view.Initial.LoginActivity


open class RootActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FamilyNoteApplication.familyNoteApplication?.setCurrentActivity(this)
    }
    fun dismissProgressDialog() {
        //Modified by Ethan on 02/22/2016, If dismissProgressDialog throw exception, still log out
        try {
            if (progressDialog != null || progressDialog!!.isShowing) {
                progressDialog?.dismiss()
            }
        } catch (e: Exception) {
            return
        }

    }

    fun isProgressDialogShowing(): Boolean {
        when (progressDialog == null) {
            true -> return false
            false -> return true
        }
    }

    fun showAlertBox(errorMessage: String, title : String) {
        showAlertBox(errorMessage, title, 0)
    }

    fun showAlertBox(alertMessage: String?, title: String?, alignment: Int, onDismissListener: DialogInterface.OnDismissListener?) {
        var alertMessage = alertMessage
        try {
            if (alertMessage == null) alertMessage = ""

            val builder = AlertDialog.Builder(this)
            if (title == null) {
                //
            } else {
                val titleText = TextView(this)
                titleText.text = "\n" + title
                titleText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
                titleText.setTypeface(null, Typeface.BOLD)
                titleText.gravity = Gravity.CENTER
                builder.setCustomTitle(titleText)
            }

            builder.setMessage(alertMessage)
            builder.setPositiveButton("OK", null)

            val dialog = builder.create()
            dialog.window.setLayout(1000,800)
            dialog.show()
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            val positiveButtonLL = positiveButton.layoutParams as LinearLayout.LayoutParams
            positiveButtonLL.gravity = Gravity.CENTER
            positiveButton.layoutParams = positiveButtonLL
            if (onDismissListener != null) {
                dialog.setOnDismissListener(onDismissListener)
            }
        } catch (e: Exception) {
        }

    }

    /**
     * @param alertMessage
     * @param title
     * @param alignment  0:left    1:center    2:right
     */
    fun showAlertBox(alertMessage: String, title: String, alignment: Int) {
        showAlertBox(alertMessage, title, alignment, null)
    }

    fun showProgressDialog(message: String?) {
        var message = message
        if (message == null) message = ""

        if (progressDialog == null) {

            try {
                progressDialog = ProgressDialog.show(this, "", message, false)
                progressDialog?.show()
            } catch (e: Exception) {
            }

        }
    }

    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun errorHandler(message: String, title: String) {
        dismissProgressDialog()
        showAlertBox(title, message)
    }

    fun validEmailPassword(email : EditText?, password : EditText?) : Boolean {

        if (email?.text.toString().isEmpty()) {
            showAlertBox("Please enter your email.", "Empty email!")
            return false
        }
        if (!isEmailValid(email?.text.toString())) {
            showAlertBox("Please enter a valid email address.", "Wrong email format!")
            return false
        }
        if (password?.text.toString().isEmpty()) {
            showAlertBox("Please enter your password.", "Empty password!")
            return false
        }
        return true
    }

    fun showMainActivity(activity : RootActivity) {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    fun showLoginActivity(activity : RootActivity) {
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {

        if (FamilyNoteApplication.familyNoteApplication?.getCurrentActivity() is LoginActivity || FamilyNoteApplication.familyNoteApplication?.getCurrentActivity() is MainActivity ) {
            moveTaskToBack(true)
        } else {
            super.onBackPressed()
        }
    }

}
package com.kelci.familynote.view.Base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.kelci.familynote.R

open class RootActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null

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
            //Modified by Jason on 04/09/2014. Check if alertMessage is null
            if (alertMessage == null) alertMessage = ""

            val builder = AlertDialog.Builder(this)
            if (title == null) {
                //
            } else {
                //builder.setTitle(title);
                val titleText = TextView(this)
                titleText.text = "\n" + title
                titleText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18f)
                titleText.setTypeface(null, Typeface.BOLD)
                titleText.gravity = Gravity.CENTER // this is required to bring it to center.
                titleText.setTextColor(ContextCompat.getColor(this, R.color.abc_primary_text_material_dark))
                builder.setCustomTitle(titleText)
            }

            builder.setMessage(alertMessage)

            val dialog = builder.show()

            dialog.show()
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
        //Modified by Jason on 04/09/2014. Check if message is null
        if (message == null) message = ""

        if (progressDialog == null) {

            try {
                progressDialog = ProgressDialog.show(this, "", message, false)
                progressDialog?.show()
            } catch (e: Exception) {
            }

        }
    }

    fun showProgressDialog(stringId: Int) {
        val stringFromResource = getString(stringId)
        showProgressDialog(stringFromResource)
    }
}
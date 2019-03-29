package com.kelci.familynote.view.base

import android.support.v4.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelci.familynote.view.initial.MainActivity

open class BaseFragment : Fragment() {

    companion object {
        const val TimeoutError = 16
    }

    val name = BaseFragment::class.java.name

    fun getMainActivity(): MainActivity? {
        try {
            val activity = activity ?: return null
            return activity as MainActivity
        } catch (e: Exception) {
            return null
        }

    }

    fun showAlertBox(message : String?, title : String) {
        var message = message
        val mainActivity = getMainActivity() ?: return
        if (message == null) {
            return
        }
        if (message == null) message = ""
        dismissProgressDialog()
        mainActivity.showAlertBox(message, title)
    }

    fun showProgressDialog(message : String)
    {
        var mainActivity = getMainActivity ()

        mainActivity?.let { mainActivity.showProgressDialog(message) }
    }

    protected fun showProgressDialog(resourceId: Int) {
        val mainActivity = getMainActivity()
        if (mainActivity != null) {
            var message = ""
            //check whether the resource exists
            try {
                message = getString(resourceId)
            } catch (e: Exception) {
            }

            mainActivity.showProgressDialog(message)
        }
    }

    fun dismissProgressDialog() {
        val mainActivity = getMainActivity()
        mainActivity?.dismissProgressDialog()
    }

    protected fun showAlertBox(message: String?, title: String, alignment: Int) {
        var message = message
        val mainActivity = getMainActivity() ?: return
        if (message == null) message = ""

        dismissProgressDialog()
        mainActivity.showAlertBox(message, title, alignment)
    }

    protected fun showAlertBox(message: String, title: String,
                               alignment: Int, onDismissListener: DialogInterface.OnDismissListener) {
        val mainActivity = getMainActivity() ?: return
        mainActivity.showAlertBox(message, title, alignment, onDismissListener)
    }

    protected fun hideKeyboard() {
        val mainActivity = getMainActivity()
        mainActivity?.hideKeyboard()
    }
}
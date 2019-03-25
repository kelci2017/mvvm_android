package com.kelci.familynote.view.Base

import android.support.v4.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kelci.familynote.view.Initial.MainActivity

open class BaseFragment : Fragment() {

    companion object {
        const val TimeoutError = 16
    }

    val name = BaseFragment::class.java.name
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i(name, "the onCreateView was called" )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        Log.i(name, "the onResume was called" )
        super.onResume()
    }

    override fun onStop() {
        Log.i(name, "the onStop was called" )
        super.onStop()
    }

    override fun onAttach(context: Context?) {
        Log.i(name, "the onAttach was called" )
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(name, "the onActivityCreated was called" )
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.i(name, "the onStart was called" )
        super.onStart()
    }

    override fun onDestroyView() {
        Log.i(name, "the onDestroyView was called" )
        super.onDestroyView()
    }

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

    /**
     * showProgressDialog
     * @param resourceId
     */
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

    /**
     * showAlertBox
     * @param message
     * @param title
     * @param alignment (0:left  1:center  2:right)
     */
    protected fun showAlertBox(message: String?, title: String, alignment: Int) {
        var message = message
        val mainActivity = getMainActivity() ?: return
        if (message == null) message = ""

        dismissProgressDialog()
        mainActivity.showAlertBox(message, title, alignment)
    }

    /**
     * @param message
     * @param title
     * @param alignment
     * @param onDismissListener
     */
    protected fun showAlertBox(message: String, title: String,
                               alignment: Int, onDismissListener: DialogInterface.OnDismissListener) {
        val mainActivity = getMainActivity() ?: return
        mainActivity.showAlertBox(message, title, alignment, onDismissListener)
    }
}
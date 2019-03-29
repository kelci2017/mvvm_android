package com.kelci.familynote.model.restService.fcm

import android.app.IntentService
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import org.apache.http.HttpResponse


class RegistrationIntentService : IntentService(TAG) {
    private var token: String? = null

    override fun onHandleIntent(intent: Intent?) {

        try {
            synchronized(TAG) {
                val insatnceID = FirebaseInstanceId.getInstance().instanceId
                insatnceID.addOnCompleteListener(object : OnCompleteListener<InstanceIdResult> {
                    override fun onComplete(task: Task<InstanceIdResult>) {
                        if (!task.isSuccessful) {
                            Log.w(TAG, "getInstanceId failed", task.exception)
                            return
                        }

                        // Get new Instance ID token
                        token = task.result!!.token
                        // TODO: Implement this method to send any registration to your app's servers.
                        sendRegistrationToServer(token)
                        Log.i("Token", "*******************" + token)

                        // Subscribe to topic channels
                        try {
                            subscribeTopics(token)
                        } catch (e: Exception) {
                            Log.e(javaClass.name, e.toString())
                        }

                    }
                })
            }
        } catch (e: Exception) {
            Log.d(TAG, "Failed to complete token refresh", e)
        }
    }

    private fun sendRegistrationToServer(token: String?) {
        SendRegistrationIdTask(token).execute()
    }

    @Throws(IOException::class)
    private fun subscribeTopics(token: String?) {
        for (topic in TOPICS) {

            FirebaseMessaging.getInstance().subscribeToTopic(topic)
        }
    }

    private inner class SendRegistrationIdTask(private val mRegId: String?) : AsyncTask<String, Void, HttpResponse>() {

        override fun doInBackground(vararg regIds: String): HttpResponse? {

            //Modified by Jason on 06/23/2017. Use new UPNS webservice(token).
//            ServiceUtil.registerNotification(token, user_id, object : RestHandler() {
//                fun onReturn(result: RestResult) {
//                    if (result.isSuccess()) {
//                        //
//                    } else {
//                        Log.i(javaClass.name, "Error message for registerNotification:" + result.getErrorDesc())
//                    }
//                }
//            })

            return null
        }

        override fun onPostExecute(response: HttpResponse?) {
            if (response == null) {
                Log.e("SendIDToServerTask", "HttpResponse is null")
                return
            }

            val httpStatus = response!!.getStatusLine()
            if (httpStatus.getStatusCode() !== 200) {
                Log.e("SendIDToServerTask", "Status: " + httpStatus.getStatusCode())
                return
            }

            try {
                val responseContent = response!!.getEntity().getContent().toString()
                val responseStatues = response!!.getStatusLine().toString()

                Log.i("Response From Server: ", responseContent)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    companion object {
        private val TAG = "RegIntentService"
        private val TOPICS = arrayOf("global")
    }
}
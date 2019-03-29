package com.kelci.familynote.model.restService.fcm

import android.content.Intent
import com.google.firebase.iid.FirebaseInstanceIdService

class MyInstanceIDListenerService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val intent = Intent(this, RegistrationIntentService::class.java)
        startService(intent)
    }

}
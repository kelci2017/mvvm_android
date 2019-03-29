package com.kelci.familynote.model.restService.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFcmListenerService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage?) {
        val from = message!!.from
        val data = message.data

        val message_content = data["message"].toString()

        if (message_content != null) {
            NotificationUtil.sendToSystemNotification(applicationContext, message_content)
        }
    }

}
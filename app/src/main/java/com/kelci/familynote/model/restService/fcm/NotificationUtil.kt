package com.kelci.familynote.model.restService.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.kelci.familynote.FamilyNoteApplication
import com.kelci.familynote.R
import com.kelci.familynote.utilities.CommonUtil
import com.kelci.familynote.view.base.BlankActivity
import java.text.SimpleDateFormat

class NotificationUtil {
    companion object {
        fun sendToSystemNotification(context: Context, message: String) {

            val intent = Intent(context, BlankActivity::class.java)

            //Modified by Jason on 05/16/2016. Add recodeCode.
            val requestCode = CommonUtil.generateRandom()

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val id = "my_package_channel_1"
            val name = "my_package_channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            //var mChannel: NotificationChannel? = null
            var builder: NotificationCompat.Builder

            // Get the layouts to use in the custom notification
            val notificationLayout = RemoteViews(context.packageName, R.layout.system_notification)
            val notificationLayoutExpanded = RemoteViews(context.packageName, R.layout.system_notification)

            // Apply the layouts to the notification
//            val customNotification = NotificationCompat.Builder(context, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.notification_icon)
//                    .setStyle(NotificationCompat.DecoratedCustomViewStyle())
//                    .setCustomContentView(notificationLayout)
//                    .setCustomBigContentView(notificationLayoutExpanded)
//                    .build()

            builder = NotificationCompat.Builder(context, id)
                    .setSmallIcon(R.drawable.ic_noteimage)
                    .setTicker("FamilyNoteApp alert")
                    .setAutoCancel(true)
                    .setDefaults(Notification.FLAG_SHOW_LIGHTS)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setLights(-0xffff01, 100, 3000)
                    .setContentIntent(pendingIntent)
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                mChannel = NotificationChannel(id, name, importance)
//            }
            builder = builder.setContent(getComplexNotificationView(context, message))

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                if (mChannel != null)
//                    notificationManager.createNotificationChannel(mChannel)
//            }
            notificationManager.notify(requestCode, builder.build())
        }

        private fun getComplexNotificationView(context: Context, message: String): RemoteViews {
            // Using RemoteViews to bind custom layouts into Notification
            val notificationView = RemoteViews(context.packageName, R.layout.system_notification)
            notificationView.setTextViewText(R.id.title, "FamilyNoteApp alert")
            notificationView.setTextViewText(R.id.notebody, message)

            return notificationView
        }
    }
}
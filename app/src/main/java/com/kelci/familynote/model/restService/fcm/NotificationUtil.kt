package com.kelci.familynote.model.restService.fcm

import android.app.Notification
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

            val requestCode = CommonUtil.generateRandom()

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val id = "my_package_channel_1"
            var builder: NotificationCompat.Builder

            builder = NotificationCompat.Builder(context, id)
                    .setSmallIcon(R.drawable.ic_noteimage)
                    .setTicker("FamilyNoteApp alert")
                    .setAutoCancel(true)
                    .setDefaults(Notification.FLAG_SHOW_LIGHTS)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setLights(-0xffff01, 100, 3000)
                    .setContentIntent(pendingIntent)

            builder = builder.setContent(getComplexNotificationView(context, message))

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(requestCode, builder.build())
        }

        private fun getComplexNotificationView(context: Context, message: String): RemoteViews {
            // Using RemoteViews to bind custom layouts into Notification
            val notificationView = RemoteViews(context.packageName, R.layout.notification)
            notificationView.setImageViewResource(R.id.imagenotileft, R.drawable.ic_familynote)
            notificationView.setTextViewText(R.id.title, "FamilyNoteApp alert")
            notificationView.setTextViewText(R.id.notebody, message)

            val dateLocalizedFormatPattern = SimpleDateFormat().toLocalizedPattern()

            val simpleDateFormat = SimpleDateFormat(dateLocalizedFormatPattern)
            notificationView.setTextViewText(R.id.time, simpleDateFormat.format(java.util.Date()))

            notificationView.setTextColor(R.id.time, FamilyNoteApplication.familyNoteApplication?.resources!!.getColor(R.color.background_material_dark))

            return notificationView
        }
    }
}
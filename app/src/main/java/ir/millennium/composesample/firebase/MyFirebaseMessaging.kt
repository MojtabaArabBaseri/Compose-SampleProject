package ir.millennium.composesample.firebase

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ir.millennium.composesample.R
import ir.millennium.composesample.ui.activities.mainActivity.MainActivity
import timber.log.Timber

class MyFirebaseMessaging : FirebaseMessagingService() {

    private val notificationManager: NotificationManager by lazy {
        getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag("newTokenFirebase").d(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.data.isNotEmpty().let {
            val title = remoteMessage.data["title"]
            val body = remoteMessage.data["body"]
            var channelId = remoteMessage.data["channelId"]
            if (!isChannelIdValid(channelId)) {
                channelId = GENERAL_CHANNEL_ID
            }
            generateNotification(title, body, channelId!!)
        }
    }

    private fun generateNotification(title: String?, body: String?, channelId: String) {

        val builderNotification = NotificationCompat.Builder(this, channelId)
        val intentMessage = Intent(this, MainActivity::class.java)
        intentMessage.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pendingIntent = PendingIntent.getActivity(
            this,
            (System.currentTimeMillis()).toInt(),
            intentMessage,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        builderNotification
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(body)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setPriority(Notification.DEFAULT_ALL)
//            .setOngoing(true) // this is not allow user remove notification
            .setContentIntent(pendingIntent)

        val notification = builderNotification.build()
        notificationManager.notify((1..1000).random(), notification)
    }

    private fun isChannelIdValid(channelId: String?): Boolean {
        val channelList = notificationManager.notificationChannels
        for (channel in channelList) {
            if (channel.id == channelId) {
                return true
            }
        }
        return false
    }
}
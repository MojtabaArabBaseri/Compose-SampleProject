package ir.millennium.composesample.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val GENERAL_CHANNEL_ID = "general_notification_channel"
const val ADVERTISE_CHANNEL_ID = "advertise_notification_channel"

class ManageChannelNotification @Inject constructor(@ApplicationContext val applicationContext: Context) {

    fun createAllChannelNotification() {
        createGeneralNotificationChannel()
        createAdvertiseNotificationChannel()
    }

    private fun createGeneralNotificationChannel() {
        val channelId = GENERAL_CHANNEL_ID
        val channelName = "General Notification"
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        channel.enableLights(true)
        channel.lightColor = android.graphics.Color.RED
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 800)
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createAdvertiseNotificationChannel() {
        val channelId = ADVERTISE_CHANNEL_ID
        val channelName = "Advertise Notification"
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        channel.enableLights(true)
        channel.lightColor = android.graphics.Color.CYAN
        channel.enableVibration(true)
        channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 800)
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
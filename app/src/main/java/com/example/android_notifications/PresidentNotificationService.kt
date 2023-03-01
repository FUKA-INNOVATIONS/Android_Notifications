package com.example.android_notifications

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class PresidentNotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(id: Int, title: String, content: String) {

        // action on notification click/tap
        val activityIntent = Intent(context, MainActivity::class.java)

        // OR pendingIntent -> A description of an Intent and target action to perform with it.
        // Can be triggered by external sources, iex. Broadcast, service OR cancel erc..  > Flags
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            id, // Unique notification id (request code), can be use for example to update etc..
            activityIntent,
            // From on API v (30+ or so) we need to specify flag mutable/immutable meaning ..
            // that the receiver of this intent can or can't directly manipulate this intent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_UPDATE_CURRENT else 0
        )

        val likeIntent = PendingIntent.getBroadcast(
            context,
            id,
            // Intent(context, LikeNotificationReceiver::class.java),
            // Intent with extra data
            Intent(context, LikeNotificationReceiver::class.java).apply {
                putExtra("dataName", 13) // Sent to the receiver inside intent param
            },
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_UPDATE_CURRENT else 0
        )

        // Build the notification
        val notification = NotificationCompat.Builder(context, PRESIDENT_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_eco_24)
            .setContentTitle(title)
            .setContentText("$content id $id")
            .setContentIntent(activityPendingIntent)
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Like!",
                likeIntent
            )
            .build()

        // Show the notification
        notificationManager.notify(id, notification)

//            .setStyle( // There are multiple styles, check docs
//                Notification.BigTextStyle
//            )
    }

    companion object {
        const val PRESIDENT_CHANNEL_ID = "president_channel"
    }

}
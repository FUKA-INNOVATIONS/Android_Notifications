package com.example.android_notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class LikeNotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val receivedExtraData = intent?.getIntExtra("dataName", 30).toString()

        val service = PresidentNotificationService(context)
        service.showNotification(id = 33, title = "Pelaako muistisi? (likeCounter = ${LikeCounter.value})", content = "Tänään (extraData = $receivedExtraData) on presidentti X:n synttärit!")
    }
}
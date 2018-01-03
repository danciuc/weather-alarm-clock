package com.example.android.weatheralarmclock.services

import com.google.firebase.messaging.FirebaseMessagingService
import android.util.Log
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.i(Companion.TAG, "From: " + remoteMessage!!.from!!)

        if (remoteMessage.notification != null) {
            Log.i(Companion.TAG, "Message Notification Body: " + remoteMessage.notification!!.body!!)
        }
    }

    companion object {
        private val TAG: String = "FirebaseNotification"
    }
}
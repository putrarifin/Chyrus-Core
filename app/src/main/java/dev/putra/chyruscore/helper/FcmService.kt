package dev.putra.chyruscore.helper

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FcmService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("From: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: ${remoteMessage.data}")

            NotificationHelper(context = applicationContext).sendLocalNotification(remoteMessage.data.getValue("name"))
        }

        remoteMessage.notification?.let {
            Timber.d("Message Notification Body: ${it.body}")
        }

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("token: $token")
    }

}
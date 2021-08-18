package com.forumias.beta;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("Msg============> ", "Message received ["+remoteMessage+"]");


   showNotification(remoteMessage.getNotification().getBody());
    }

    private void showNotification(String message) {
        Log.e("data notification==> " , message);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"MyNotification")
                .setSmallIcon(R.drawable.ic_about)
                .setContentTitle("ForumIAS")
                .setContentText(message)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1410, notificationBuilder.build());
    }
}

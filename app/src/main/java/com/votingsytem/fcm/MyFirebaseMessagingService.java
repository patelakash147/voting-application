package com.votingsytem.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.votingsytem.R;
import com.votingsytem.HomeActivity;
import com.votingsytem.SplashActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG = getClass().getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getData());
        sendNotification(remoteMessage.getData().get("message"), remoteMessage.getData().get("title"));
    }

    Handler handler;

    private void sendNotification(String message, String title) {
        try {
            Intent intent;
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            intent = new Intent(this, SplashActivity.class);
            stackBuilder.addParentStack(SplashActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ecnew);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ecnew)
                    .setContentTitle(title)
                    .setContentText(message).setLargeIcon(icon)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri).setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(resultPendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
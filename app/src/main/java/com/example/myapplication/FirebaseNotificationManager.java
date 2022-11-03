package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotificationManager extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.e("nfmss",message.getFrom());
        if (message.getNotification() !=null){
            showNotifications(message.getNotification().getTitle(),message.getNotification().getBody());

        }
    }
    private RemoteViews getCustomDesign(String title,
                                        String body) {
        RemoteViews remoteViews = new RemoteViews(
                getApplicationContext().getPackageName(),
                R.layout.notification);
        remoteViews.setTextViewText(R.id.nftitle, title);
        remoteViews.setTextViewText(R.id.nfmess, body);
        remoteViews.setImageViewResource(R.id.nficon,
                R.drawable.bibimbap);
        return remoteViews;
    }
    private void showNotifications(String title, String body) {
        Intent intent = new Intent(this,MainActivity.class);
        String channel_id = "Notification Channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder
                = new NotificationCompat
                .Builder(getApplicationContext(),
                channel_id)
                .setSmallIcon(R.drawable.bibimbap)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000,
                        1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);
        // Check if the Android Version is greater than JELLY_BEAN
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            builder = builder.setContent(getCustomDesign(title, body));
        }
        else {
            builder = builder.setContentTitle(title).setContentText(body).setSmallIcon(R.drawable.bibimbap);
        }
        NotificationManager notificationManager
                = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel
                    = new NotificationChannel(
                    channel_id, "web_app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(
                    notificationChannel);
        }
        notificationManager.notify(0,builder.build());

    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.e("newtknn",token);
        super.onNewToken(token);
    }
}

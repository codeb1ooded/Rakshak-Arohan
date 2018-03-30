package com.aarushi.crime_mappingapp.upload;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.aarushi.crime_mappingapp.DashboardActivity;
import com.aarushi.crime_mappingapp.R;

/**
 * Created by megha on 30/03/18.
 */

public class AutoUploadService extends Service {
    public AutoUploadService() {
    }

    private static final int NOTIF_ID = 6;

    public static boolean AUTO_UPLOAD_SERVICE = false;

    @Override
    public int onStartCommand(final Intent intent, final int flags, int startId) {

        String title = "Market Acquire: Uploading..";
        String message = "Uploading Forms";
        startForeground(NOTIF_ID, getNotification(title, message).build());


        // TODO: upload process

        return START_NOT_STICKY;
    }

    private NotificationCompat.Builder getNotification(String title, String text) {
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentText(text)
               // .setSmallIcon(R.drawable.)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text));

    }

    /**
     * Method to create notification with the updated message
     * @param isUploaded true if the upload was successful and false if there was an error
     */
    private void updateNotification(boolean isUploaded) {
        String text;

        if (isUploaded) {
            text = "All the complaints have been uploaded.";
        } else {
            text = "Some of the complaints couldn't be uploaded due to an error.";
        }
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        Notification notification = getNotification("Market Acquire", text)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();


        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIF_ID, notification);

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


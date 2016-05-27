//package com.swiftpot.android.teletrosky.services;/**
// * Created by Rodney on 10-Apr-16.
// */
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import com.google.android.gms.gcm.GcmListenerService;
//import com.swiftpot.android.tariffplanner.R;
//import com.swiftpot.android.tariffplanner.activities.GeneralNotifMessageActivity;
//import com.swiftpot.android.tariffplanner.extras.CustomStrings;
//
///**
// * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 10-Apr-16
// * 8:31 PM
// */
//public class CustomGcmListenerService extends GcmListenerService {
//    private final String TAG = this.getClass().getName();
//    private final String FULL_NOTIF_MSG = "FULL_NOTIF_MSG";
//    @Override
//    public void onMessageReceived(String from, Bundle data) {
//        String message = data.getString("message");
//        Log.d(TAG, "From: " + from);
//        Log.d(TAG, "Message: " + message);
//
//        if (from.startsWith("/topics/")) {
//            // message received from some topic.
//        } else {
//            // normal downstream message.
//        }
//
//        // [START_EXCLUDE]
//        /**
//         * Production applications would usually process the message here.
//         * Eg: - Syncing with server.
//         *     - Store message in local database.
//         *     - Update UI.
//         */
//
//        /**
//         * In some cases it may be useful to show a notification indicating to the user
//         * that a message was received.
//         */
//        sendNotification(message);
//        // [END_EXCLUDE]
//    }
//
//    // [END receive_message]
//
//    /**
//     * Create and show a simple notification containing the received GCM message.
//     *
//     * @param message GCM message received.
//     */
//    private void sendNotification(String message) {
//        Intent intent = new Intent(this, GeneralNotifMessageActivity.class);
//        intent.putExtra(CustomStrings.FULL_NOTIF_MSG,message);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_tv)
//                .setContentTitle("Tarriff Schedule")
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
//
//}

package co.in.mobilepay.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.concurrent.atomic.AtomicInteger;

import co.in.mobilepay.R;
import co.in.mobilepay.view.activities.HomeActivity;

/**
 * Created by Nithish on 15-03-2016.
 */
public class NotificationService extends GcmListenerService {
    private final static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("", "");

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(Long.toString(System.currentTimeMillis()));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
               // .setSmallIcon(R.drawable.mterrago_icon)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("New TaskNote assigned to you:\n")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(atomicInteger.incrementAndGet() /* ID of notification */, notificationBuilder.build());
    }
}

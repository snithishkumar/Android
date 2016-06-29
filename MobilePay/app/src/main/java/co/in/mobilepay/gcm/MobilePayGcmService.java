package co.in.mobilepay.gcm;

import android.accounts.Account;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

import co.in.mobilepay.R;
import co.in.mobilepay.dao.NotificationDao;
import co.in.mobilepay.dao.impl.NotificationDaoImpl;
import co.in.mobilepay.entity.NotificationEntity;
import co.in.mobilepay.enumeration.NotificationType;
import co.in.mobilepay.sync.MobilePaySyncAdapter;
import co.in.mobilepay.view.activities.NotificationActivity;

/**
 * Created by Nithishkumar on 4/20/2016.
 * Ref : http://stackoverflow.com/questions/28276606/handling-multiple-notification-in-android
 * Ref : http://stackoverflow.com/questions/33040737/how-to-group-android-notifications-like-whatsapp
 */
public class MobilePayGcmService extends GcmListenerService {

    private Gson gson = null;

    @Override
    public void onMessageReceived(String from, Bundle data) {
if(gson == null){
    gson = new Gson();
}
        //gcm.notification.
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setMessage(data.getString("message"));
        notificationEntity.setNotificationType(NotificationType.valueOf(data.getString("notificationType")));
        notificationEntity.setPurchaseGuid(data.getString("purchaseGuid"));
        insertNotification(notificationEntity);
        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(notificationEntity);
        // [END_EXCLUDE]
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param notificationEntity GCM message received.
     */
    private void sendNotification(NotificationEntity notificationEntity) {

        // Creates an explicit intent for an Activity in your app
        Intent intent = new Intent(this, NotificationActivity.class);
       // intent.putExtra("notificationType",notificationEntity.getNotificationType().getNotificationType());
        intent.putExtra("purchaseUuid",notificationEntity.getPurchaseGuid());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this,
                        0,
                        intent,
                        PendingIntent.FLAG_ONE_SHOT
                );

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("MobilePay")
                .setContentText(notificationEntity.getMessage())
                .setSmallIcon(R.mipmap.mobilepay_logo)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(resultPendingIntent);

       Notification notification =  notificationBuilder.build();
       /* NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);*/

       // notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationEntity.getNotificationType().getNotificationType(), notification);
    }

    private void insertNotification(NotificationEntity notificationEntity){
        try{
            NotificationDao  notificationDao = new NotificationDaoImpl(this);
            notificationDao.createNotification(notificationEntity);
            Account account = MobilePaySyncAdapter.getSyncAccount(this);

            Bundle settingsBundle = new Bundle();
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_MANUAL, true);
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

            ContentResolver.requestSync(account, getString(R.string.auth_type), settingsBundle);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

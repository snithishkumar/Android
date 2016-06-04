package co.in.mobilepay.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import co.in.mobilepay.bus.MobilePayBus;

/**
 * Created by Nithishkumar on 5/4/2016.
 */
public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[])bundle.get("pdus");
            final SmsMessage[] messages = new SmsMessage[pdus.length];

            for (int i = 0; i < pdus.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                }
                else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                String phoneNumber = messages[i].getOriginatingAddress();// -- TODO
                String message = messages[i].getMessageBody();
                int pos = message.indexOf("is");
                if(pos > -1){
                    message =  message.substring(0,pos);
                    message =   message.trim();
                    MobilePayBus.getInstance().post(message);
                }

                break;
            }
        }
    }
}

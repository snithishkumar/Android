package co.in.mobilepay.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.util.Base64;


import org.joda.time.DateTime;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Nithish on 23-01-2016.
 */
public class ServiceUtil {

    public static  String netEncryption(String data)throws Exception{
        String alg = "AES";
        String key = "1234567891123456";
        return encryption(data,key,alg);
    }


    public static String netDecryption(String data) throws Exception{
        String alg = "AES";
        String key = "1234567891123456";
        return decryption(data, key, alg);

    }


    private static String encryption(String data,String key,String alg)throws Exception{
        Key key2  = new SecretKeySpec(key.getBytes(), alg);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, key2);
        byte[] encVal =  cipher.doFinal(data.getBytes());
        String encryptedValue  = Base64.encodeToString(encVal, Base64.NO_WRAP);
        return encryptedValue;
    }


    private static String decryption(String data,String key,String alg) throws Exception{
        Key key2  = new SecretKeySpec(key.getBytes(), alg);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.DECRYPT_MODE, key2);
        byte[] decryptedByte =  cipher.doFinal(Base64.decode(data.getBytes(), Base64.NO_WRAP));
        String result = new String(decryptedByte);
        return result;
    }

    public static String getIMEINumber(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static String getDateTimeAsString(String dateTime){
        DateTime date = DateTime.now();
        int dayOfMonth = date.getDayOfMonth();
        date = date.withMillis(Long.valueOf(dateTime));
        int givenDayOfMonth = date.getDayOfMonth();
        if(dayOfMonth == givenDayOfMonth){
            return "Today,"+getHours(date.toDate());
        }
        return getDayHours(date.toDate());
    }

    private static String getHours(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        return  simpleDateFormat.format(date);
    }

    private static String getDayHours(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM dd, hh:mm a");
        return  simpleDateFormat.format(date);
    }

    public static Long getCurrentTimeMilli(){
        DateTime date = DateTime.now();
        return date.getMillis();
    }


}

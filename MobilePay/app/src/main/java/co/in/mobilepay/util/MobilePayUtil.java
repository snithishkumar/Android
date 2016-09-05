package co.in.mobilepay.util;

import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.in.mobilepay.R;

/**
 * Created by Nithish on 11-03-2016.
 */
public class MobilePayUtil {



    public static String formatDate(long dateTime){
        Date date = new Date(dateTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyy, hh:mm a");
        return simpleDateFormat.format(date);
    }

    public static String thousandSeparator(AppCompatActivity appCompatActivity,String value){
       return thousandSeparator(appCompatActivity,Float.valueOf(value));
    }


    public static String thousandSeparator(AppCompatActivity appCompatActivity,float value){
      return   appCompatActivity.getResources().getString(R.string.indian_rupee_symbol)+""+String.format("%,.2f", value);
    }

    public static String thousandSeparator(AppCompatActivity appCompatActivity,double value){
        return thousandSeparator(appCompatActivity,(float) value);
    }
}

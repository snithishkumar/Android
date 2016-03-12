package co.in.mobilepay.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nithish on 11-03-2016.
 */
public class MobilePayUtil {


    public static String formatDate(long dateTime){
        Date date = new Date(dateTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyy, hh:mm a");
        return simpleDateFormat.format(date);
    }
}

package co.in.mobilepay.view.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Nithish on 07-02-2016.
 */
public class ActivityUtil {

    public static ProgressDialog showProgress(String title,String message,Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }


    public static void showDialog(final AppCompatActivity context,final String title,final String message){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                // Setting Dialog Title
                alertDialog.setTitle(title);

                // Setting Dialog Message
                alertDialog.setMessage(message);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        });


    }
}

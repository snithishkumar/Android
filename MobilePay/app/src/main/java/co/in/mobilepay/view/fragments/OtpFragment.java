package co.in.mobilepay.view.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.listener.SMSReceiver;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.MainActivity;

/**
 * Created by Nithish on 07-02-2016.
 */
public class OtpFragment extends Fragment implements View.OnClickListener {

    private EditText otpNumber;
    private MainActivity mainActivity = null;
    private MainActivityCallback mainActivityCallback = null;
    private ProgressDialog progressDialog = null;
    private Button otpReset;

    private boolean isOtpReceived = false;

    private  BroadcastReceiver mySmsReceiver = null;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    public OtpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        checkPermission();
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        otpNumber = (EditText) view.findViewById(R.id.otp_number);
        Button otpSubmit = (Button) view.findViewById(R.id.otp_submit);
        otpSubmit.setOnClickListener(this);

        otpReset = (Button) view.findViewById(R.id.otp_resend);
        otpReset.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isOtpReceived) {
                    otpReset.setEnabled(true);
                }

            }
        }, 10000);
        otpReset.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
        this.mainActivityCallback = mainActivity;
    }

    public String getOtpNumber() {
        String optNumberTemp = otpNumber.getText().toString();
        if (optNumberTemp == null || optNumberTemp.trim().isEmpty()) {
            otpNumber.setError(getString(R.string.error_otp_number));
            return null;
        }
        return optNumberTemp;
    }


    @Subscribe
    public void processOtpResponse(String otpResponse) {
        isOtpReceived = true;
        otpReset.setEnabled(false);
        otpNumber.setText(otpResponse);
    }

    @Subscribe
    public void processOtpResponse(ResponseData responseData) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        switch (responseData.getStatusCode()) {
            case MessageConstant.OTP_OK:
                mainActivityCallback.success(MessageConstant.OTP_OK, null);
                break;
            case MessageConstant.OTP_ERROR_CODE:
                ActivityUtil.showDialog(mainActivity, "Error", MessageConstant.OTP_ERROR);
                break;

            default:
                // Toast
                Toast.makeText(mainActivity, responseData.getData(), Toast.LENGTH_LONG).show();
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.otp_submit:
                String otpNumber = getOtpNumber();
                if (otpNumber != null) {
                    boolean isNet = ServiceUtil.isNetworkConnected(mainActivity);
                    if (isNet) {
                        progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", mainActivity);
                        if (mainActivity.getRegisterJson() != null) {
                            mainActivity.getAccountService().validateOtp(otpNumber, mainActivity.getRegisterJson(), mainActivity);
                        } else {
                            mainActivity.getAccountService().validateOtp(otpNumber, mainActivity.getMobileNumber(), mainActivity);
                        }
                    } else {
                        ActivityUtil.showDialog(mainActivity, "No Network", "Check your connection.");
                    }
                }
                break;
            case R.id.otp_resend:
                boolean isNet = ServiceUtil.isNetworkConnected(mainActivity);
                if (isNet) {
                    if (mainActivity.getRegisterJson() != null) {
                        mainActivity.getAccountService().requestOtp(mainActivity.getRegisterJson().getMobileNumber(), mainActivity);
                    }else{
                        mainActivity.getAccountService().requestOtp(mainActivity.getMobileNumber(), mainActivity);
                    }

                    ActivityUtil.toast(mainActivity, getString(R.string.otp_resend));
                } else {
                    ActivityUtil.showDialog(mainActivity, "No Network", "Check your connection.");
                }
                break;

        }
    }


    @Override
    public void onPause() {
        if(mySmsReceiver != null){
            mainActivity.unregisterReceiver(mySmsReceiver);
        }
        MobilePayBus.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        if(mySmsReceiver != null){
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            mainActivity.registerReceiver(mySmsReceiver, filter);
        }
        MobilePayBus.getInstance().register(this);
        super.onResume();
    }



    private void requestPermission(){
        requestPermissions(new String[] {Manifest.permission.READ_SMS},
                REQUEST_CODE_ASK_PERMISSIONS);
    }

    private void registerReceiver(){
        mySmsReceiver = new SMSReceiver();
    }


    private void checkPermission() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(mainActivity,
                Manifest.permission.READ_SMS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(mainActivity,Manifest.permission.READ_SMS)) {
                showMessageOKCancel("You need to allow access IMEI Number",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermission();
                            }
                        });
                return;
            }

            requestPermission();
            return;
        }else{
            registerReceiver();
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mainActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    registerReceiver();
                } else {
                    // Permission Denied
                    Toast.makeText(mainActivity, "Unable to read IMEI Number", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    public interface MainActivityCallback {
        void success(int code, Object data);
    }
}

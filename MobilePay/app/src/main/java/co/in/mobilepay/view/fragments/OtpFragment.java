package co.in.mobilepay.view.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import co.in.mobilepay.service.impl.AccountServiceImpl;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.MainActivity;
import retrofit2.Response;

/**
 * Created by Nithish on 07-02-2016.
 */
public class OtpFragment extends Fragment implements View.OnClickListener{

    private EditText otpNumber;
    private MainActivity mainActivity =  null;
    private MainActivityCallback mainActivityCallback = null;
    private ProgressDialog progressDialog = null;
    private String mobileNumber = null;
    private Boolean isProfileUpdate = false;
    private Button  otpReset;

    private boolean isOtpReceived = false;

    private final BroadcastReceiver mySmsReceiver = new SMSReceiver();


    public OtpFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mobileNumber = getArguments().getString("mobileNumber");
        isProfileUpdate = getArguments().getBoolean("isProfileUpdate");
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        otpNumber = (EditText) view.findViewById(R.id.otp_number);
        Button otpSubmit = (Button)view.findViewById(R.id.otp_submit);
        otpSubmit.setOnClickListener(this);

        otpReset = (Button)view.findViewById(R.id.otp_resend);
        otpReset.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isOtpReceived){
                    otpReset.setEnabled(true);
                }

            }
        },30000);
        otpReset.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity)context;
        this.mainActivityCallback = mainActivity;
    }
    public String getOtpNumber(){
        String optNumberTemp = otpNumber.getText().toString();
        if(optNumberTemp == null || optNumberTemp.trim().isEmpty()){
            otpNumber.setError(getString(R.string.error_otp_number));
            return null;
        }
        return optNumberTemp;
    }


    @Subscribe
    public void processOtpResponse(String otpResponse){
        isOtpReceived = true;
        otpReset.setEnabled(false);
        otpNumber.setText(otpResponse);
    }

    @Subscribe
    public void processOtpResponse(ResponseData responseData){
        progressDialog.dismiss();
        switch (responseData.getStatusCode()){
            case MessageConstant.OTP_OK:
                // Success
                if(isProfileUpdate){
                    mainActivityCallback.success(MessageConstant.PROFILE_UPDATE_SUCCESS,mobileNumber);
                }else{
                    mainActivityCallback.success(MessageConstant.OTP_OK,mobileNumber);
                }

                break;
            case  MessageConstant.OTP_ERROR_CODE :
                ActivityUtil.showDialog(mainActivity,"Error",MessageConstant.OTP_ERROR);
                break;

            default:
                // Toast
                Toast.makeText(mainActivity,responseData.getData(),Toast.LENGTH_LONG).show();
                break;
        }
    }



    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.otp_submit:
        String otpNumber = getOtpNumber();
        if(otpNumber != null){
            boolean isNet = ServiceUtil.isNetworkConnected(mainActivity);
            if(isNet){
                progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", mainActivity);
                mainActivity.getAccountService().validateOtp(otpNumber,mobileNumber);

            }else{
                ActivityUtil.showDialog(mainActivity, "No Network", "Check your connection.");
            }
        }

        break;
    case R.id.otp_resend:
        if(mobileNumber != null){
            boolean isNet = ServiceUtil.isNetworkConnected(mainActivity);
            if(isNet){
                mainActivity.getAccountService().resendOtp(mobileNumber);
                ActivityUtil.toast(mainActivity,getString(R.string.otp_resend));
            }else{
                ActivityUtil.showDialog(mainActivity, "No Network", "Check your connection.");
            }
        }
        break;

}
    }


    @Override
    public void onPause() {
        mainActivity.unregisterReceiver(mySmsReceiver);
        MobilePayBus.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        mainActivity.registerReceiver(mySmsReceiver, filter);

        MobilePayBus.getInstance().register(this);
        super.onResume();
    }

    public  interface MainActivityCallback {
        void success(int code,Object data);
    }
}

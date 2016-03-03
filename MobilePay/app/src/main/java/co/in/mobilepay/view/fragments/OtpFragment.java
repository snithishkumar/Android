package co.in.mobilepay.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.in.mobilepay.R;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.AccountServiceImpl;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.MainActivity;
import retrofit2.Response;

/**
 * Created by Nithish on 07-02-2016.
 */
public class OtpFragment extends Fragment implements View.OnClickListener,AccountServiceImpl.AccountServiceCallback{

    private EditText otpNumber;
    private MainActivity mainActivity =  null;
    MainActivityCallback mainActivityCallback = null;
    ProgressDialog progressDialog = null;
    String mobileNumber = null;
    public OtpFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mobileNumber = getArguments().getString("mobileNumber");
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        otpNumber = (EditText) view.findViewById(R.id.otp_number);
        Button otpSubmit = (Button)view.findViewById(R.id.otp_submit);
        otpSubmit.setOnClickListener(this);
        Button  otpReset = (Button)view.findViewById(R.id.otp_resend);
       // otpReset.setOnClickListener(this);
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
        if(optNumberTemp == null || optNumberTemp.isEmpty()){
            otpNumber.setError("Otp Should not be blank");
        }
        return optNumberTemp;
    }

    @Override
    public void accountServiceCallback(int statusCode, Object data) {
        progressDialog.dismiss();
        switch (statusCode){
            case MessageConstant.OTP_OK:
                // Success
                mainActivityCallback.success(MessageConstant.OTP_OK,mobileNumber);
                break;
           case  MessageConstant.OTP_ERROR_CODE :
               ActivityUtil.showDialog(mainActivity,"Error",MessageConstant.OTP_ERROR);
            break;

           default:
               // Toast
               Toast.makeText(mainActivity,(String)data,Toast.LENGTH_LONG).show();
               break;
        }
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.otp_submit:
        String otpNumber = getOtpNumber();
        boolean isNet = ServiceUtil.isNetworkConnected(mainActivity);
        if(isNet){
            progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", mainActivity);
            mainActivity.getAccountService().validateOtp(otpNumber,mobileNumber,this);

        }else{
            ActivityUtil.showDialog(mainActivity, "No Network", "Check your connection.");
        }
        break;
    case R.id.otp_resend:
        break;

}
    }

    public  interface MainActivityCallback {
        void success(int code,Object data);
    }
}

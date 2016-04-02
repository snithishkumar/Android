package co.in.mobilepay.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import co.in.mobilepay.R;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.AccountServiceImpl;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.MainActivity;

/**
 * Created by Nithish on 29-02-2016.
 */
public class MobileFragment  extends Fragment implements View.OnClickListener,AccountServiceImpl.AccountServiceCallback {

    private TextView mobileNumber = null;
    private String mobile;
    private MainActivity mainActivity = null;
    private  Button otpSubmit = null;

    private MainActivityCallback mainActivityCallback;

    ProgressDialog progressDialog = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobile, container, false);
        otpSubmit = (Button)view.findViewById(R.id.mobile_submit);
        mobileNumber = (TextView) view.findViewById(R.id.mobile_number);
        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 10){
                    otpSubmit.setBackground(ContextCompat.getDrawable(mainActivity, R.drawable.button));
                    otpSubmit.setTextColor(ContextCompat.getColor(mainActivity, R.color.white));
                }
            }
        });

        otpSubmit.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
         mobile = mobileNumber.getText().toString();
        if(mobile == null || mobile.isEmpty()){
            mobileNumber.setError("Please enter the mobile number.");
            return;
        }
        if(mobile.length() != 10){
            mobileNumber.setError("mobile Number must be 10 digits.");
            return;
        }
        boolean isNet = ServiceUtil.isNetworkConnected(mainActivity);
        if(isNet){
            progressDialog = ActivityUtil.showProgress("In Progress", "Validating...", mainActivity);
            mainActivity.getAccountService().verifyMobile(mobile,this);
        }else{
            ActivityUtil.showDialog(mainActivity, "No Network", "Check your connection.");
        }
    }

    @Override
    public void accountServiceCallback(int statusCode, Object data) {
        progressDialog.dismiss();
        switch (statusCode){
            case MessageConstant.MOBILE_VERIFY_OK:
                mainActivityCallback.success(MessageConstant.MOBILE_VERIFY_OK,mobile);
                // Need to callback acitivity
                break;
            default:
                ActivityUtil.showDialog(mainActivity, "Error", MessageConstant.MOBILE_VERIFY_INTERNAL_ERROR_MSG);
                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity)context;
        this.mainActivityCallback = mainActivity;
    }




    public  interface MainActivityCallback {
        void success(int code,Object data);
    }
}

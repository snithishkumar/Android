package co.in.mobilepay.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.AccountService;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.MainActivity;
import co.in.mobilepay.view.activities.NaviDrawerActivity;

/**
 * Created by Nithishkumar on 3/26/2016.
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener{
    private EditText vMobileNumber;
    private EditText vProfileName;
    private EditText vPassword;

    private AccountService accountService;
    private NaviDrawerActivity naviDrawerActivity;
    private EditProfileFragmentCallBack editProfileFragmentCallBack;

    private ProgressDialog progressDialog = null;

    private boolean isPasswordChanged = false;

    private String previousMobile = null;
    private String newMobile = null;

    public EditProfileFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        naviDrawerActivity.getSupportActionBar().setTitle("Edit Profile");
        initBackButton();
        View view = inflater.inflate(R.layout.fragment_profile_update, container, false);

        init(view);
        loadData();
        return view;
    }

    private void initBackButton(){
        final Drawable upArrow = ContextCompat.getDrawable(naviDrawerActivity,R.drawable.abc_ic_ab_back_mtrl_am_alpha);;
        upArrow.setColorFilter( ContextCompat.getColor(naviDrawerActivity,R.color.white), PorterDuff.Mode.SRC_ATOP);
        naviDrawerActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        naviDrawerActivity.getSupportActionBar().setHomeButtonEnabled(true);
        naviDrawerActivity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void  init(View view){
        vMobileNumber = (EditText) view.findViewById(R.id.profile_edit_mobile_no);
        vProfileName = (EditText) view.findViewById(R.id.profile_name);
        vPassword = (EditText) view.findViewById(R.id.profile_edit_pass);
        vPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isPasswordChanged = true;
            }
        });
        Button vButton = (Button)view.findViewById(R.id.profile_edit_submit);
        vButton.setOnClickListener(this);
    }

    private void loadData(){
        UserEntity userEntity = accountService.getUserDetails();
        previousMobile = userEntity.getMobileNumber();
        vMobileNumber.setText(userEntity.getMobileNumber());
        vProfileName.setText(userEntity.getName());
        vPassword.setText(".....");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.naviDrawerActivity = (NaviDrawerActivity)context;
        editProfileFragmentCallBack = naviDrawerActivity;
        this.accountService = naviDrawerActivity.getAccountService();
    }

    @Override
    public void onClick(View v) {
        RegisterJson registerJson = new RegisterJson();
        if(isPasswordChanged){
            String password = vPassword.getText().toString();
             boolean result =   password.matches("\\d+(?:\\.\\d+)?");
            if(!result){
                vPassword.setError("Invalid Password");
                return;
            }
            registerJson.setPassword(password);
        }
        String nameTemp = vProfileName.getText().toString();
        if(nameTemp == null || nameTemp.trim().isEmpty()){
            vProfileName.setError("Name should not be blank");
            return;
        }
        registerJson.setName(nameTemp);

        newMobile = vMobileNumber.getText().toString();
        if(newMobile == null || newMobile.trim().isEmpty()){
            vMobileNumber.setError("mobile should not be blank");
            return;
        }

        if(newMobile.length() != 10){
            vMobileNumber.setError("mobile Number must be 10 digits");
            return;
        }
        registerJson.setMobileNumber(newMobile);

        String imeiNumber = ServiceUtil.getIMEINumber(naviDrawerActivity);
        registerJson.setImei(imeiNumber);
        boolean isNet = ServiceUtil.isNetworkConnected(naviDrawerActivity);
        if(isNet){
            progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", naviDrawerActivity);
            naviDrawerActivity.getAccountService().updateUser(registerJson);
        }else{
            ActivityUtil.showDialog(naviDrawerActivity, "No Network", "Check your connection.");
        }


    }

    @Subscribe
    public void processEditProResponse(ResponseData responseData){
        progressDialog.dismiss();

        switch (responseData.getStatusCode()){
            case MessageConstant.REG_OK:
                if(isPasswordChanged || !previousMobile.equals(newMobile)){
                    accountService.verifyMobile(newMobile,null);
                    editProfileFragmentCallBack.onSuccess(1,newMobile);
                    // Need to call OTP
                }else {
                    // Need to call home screen
                    editProfileFragmentCallBack.onSuccess(2,newMobile);
                }
                break;
            case MessageConstant.REG_ERROR_CODE:
                ActivityUtil.showDialog(naviDrawerActivity, "Error", MessageConstant.REG_ERROR);
                break;
            default:
                ActivityUtil.showDialog(naviDrawerActivity,"Error",responseData.getData());
                break;
        }

    }


    @Override
    public void onPause() {
        MobilePayBus.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume(){
        MobilePayBus.getInstance().register(this);
        super.onResume();
    }



    public interface EditProfileFragmentCallBack{
        void onSuccess(int option,String mobile);
    }



}

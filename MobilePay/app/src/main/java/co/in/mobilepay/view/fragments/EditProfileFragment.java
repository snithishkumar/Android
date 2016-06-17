package co.in.mobilepay.view.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    //private EditText vMobileNumber;
    private EditText vProfileName;
    private EditText vPassword;
    private EditText vEmail = null;

    private AccountService accountService;
    private NaviDrawerActivity naviDrawerActivity;


    private boolean isPasswordChanged = false;

    private ProgressDialog progressDialog = null;

    private EditProfileFragmentCallBack editProfileFragmentCallBack;

    private String previousMobile = null;
    private String newMobile = null;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


    public EditProfileFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        naviDrawerActivity.getSupportActionBar().setTitle("Edit Profile");
        initBackButton();
        View view = inflater.inflate(R.layout.fragment_profile_update, container, false);

        init(view);
        boolean isNet = ServiceUtil.isNetworkConnected(naviDrawerActivity);
        if(isNet){
            progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", naviDrawerActivity);
            naviDrawerActivity.getAccountService().getUserProfile(naviDrawerActivity);
        }else{
            loadData();
        }

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
      //  vMobileNumber = (EditText) view.findViewById(R.id.profile_edit_mobile_no);
        vEmail = (EditText) view.findViewById(R.id.profile_email);

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
      //  vMobileNumber.setText(userEntity.getMobileNumber());
        vProfileName.setText(userEntity.getName());
        vPassword.setText(userEntity.getPassword());
        vEmail.setText(userEntity.getEmail());
        isPasswordChanged = false;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.naviDrawerActivity = (NaviDrawerActivity)context;
        this.accountService = naviDrawerActivity.getAccountService();
        editProfileFragmentCallBack = naviDrawerActivity;
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
            if(password.length() < 6){
                vPassword.setError(getString(R.string.error_reg_pass_len));
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

       /* newMobile = vMobileNumber.getText().toString();
        if(newMobile == null || newMobile.trim().isEmpty()){
            vMobileNumber.setError("mobile should not be blank");
            return;
        }

        if(newMobile.length() != 10){
            vMobileNumber.setError("mobile Number must be 10 digits");
            return;
        }*/
        registerJson.setMobileNumber(previousMobile);

        String emailTemp = vEmail.getText().toString();
        if(emailTemp == null  || emailTemp.trim().isEmpty()){
            vEmail.setError(getString(R.string.error_reg_email));
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailTemp).matches()){
            vEmail.setError(getString(R.string.error_reg_email_not_valid));
            return;
        }

        registerJson.setEmail(emailTemp);

        String imeiNumber = ServiceUtil.getIMEINumber(naviDrawerActivity);
        registerJson.setImei(imeiNumber);

        if(isPasswordChanged){
            naviDrawerActivity.getAccountService().requestOtp(previousMobile, naviDrawerActivity);
            // Need to call OTP
            editProfileFragmentCallBack.onSuccess(1,registerJson);

        }else{
            boolean isNet = ServiceUtil.isNetworkConnected(naviDrawerActivity);
            if(isNet){
                progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", naviDrawerActivity);
                naviDrawerActivity.getAccountService().createUser(registerJson,naviDrawerActivity);
            }else{
                ActivityUtil.showDialog(naviDrawerActivity, "No Network", "Check your connection.");
            }
        }



    }


    @Subscribe
    public void processEditProResponse(ResponseData responseData){
        progressDialog.dismiss();

        switch (responseData.getStatusCode()){
            case MessageConstant.REG_OK:
                // Need to call home screen
                editProfileFragmentCallBack.onSuccess(2,null);
                break;
            case MessageConstant.PROFILE_OK:
                loadData();
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
        void onSuccess(int option,RegisterJson registerJson);
    }


    private void requestPermission(){
        requestPermissions(new String[] {Manifest.permission.READ_PHONE_STATE},
                REQUEST_CODE_ASK_PERMISSIONS);
    }


    private void checkPermission() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(naviDrawerActivity,
                Manifest.permission.READ_PHONE_STATE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(naviDrawerActivity,Manifest.permission.READ_PHONE_STATE)) {
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
           // syncRegistration();
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(naviDrawerActivity)
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
                   // syncRegistration();
                } else {
                    // Permission Denied
                    Toast.makeText(naviDrawerActivity, "Unable to read IMEI Number", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



}

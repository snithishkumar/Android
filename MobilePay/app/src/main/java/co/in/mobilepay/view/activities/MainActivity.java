package co.in.mobilepay.view.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import co.in.mobilepay.R;
import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.AccountService;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.AccountServiceImpl;
import co.in.mobilepay.view.fragments.FragmentsUtil;
import co.in.mobilepay.view.fragments.OtpFragment;
import co.in.mobilepay.view.fragments.RegistrationFragment;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AccountService accountService;

    RegistrationFragment registrationFragment = null;
    OtpFragment otpFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_main);
    }

    /**
     * Initialize service layer
     */
    private void init(){
        try{
            if(accountService == null){
                accountService = new AccountServiceImpl(this);
            }
        }catch (Exception e){
            Log.e("Error","Error in init",e);
        }

    }

    private void showFragment(){
        boolean isUserPresent = accountService.isUserPresent();
        if(isUserPresent){

        }else{
            RegistrationFragment  registrationFragment = new RegistrationFragment();
            FragmentsUtil.addFragment(this,registrationFragment,R.id.main_container);
        }
    }

    // Click Event
    public void userRegister(View view){
        RegisterJson registerJson = registrationFragment.getRegistrationJson();
        if(registerJson != null){
            String imeiNumber = ServiceUtil.getIMEINumber(this);
            registerJson.setImei(imeiNumber);
            boolean isNet = ServiceUtil.isNetworkConnected(this);
            if(isNet){
                ProgressDialog progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", this);
                Response<ResponseData> responseData = accountService.createUser(registerJson);
                progressDialog.dismiss();
                if(responseData == null){
                    ActivityUtil.showDialog(this,"Error","Something went wrong.");
                }else{
                    int code = responseData.code();
                    if(code != 200){
                        ActivityUtil.showDialog(this,"Error",responseData.message());
                    }else {
                        // OTP Password Screen
                        OtpFragment otpFragment = new OtpFragment();
                        FragmentsUtil.replaceFragment(this,otpFragment,R.id.main_container);
                    }
                }
            }else{
               ActivityUtil.showDialog(this, "No Network", "Check your connection.");
            }
        }
    }

    public void otpValidation(View view){
        switch (view.getId()){
            case R.id.otp_submit:
                String otpNumber = otpFragment.getOtpNumber();
                boolean isNet = ServiceUtil.isNetworkConnected(this);
                if(isNet){
                    ProgressDialog progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", this);
                    Response<ResponseData> responseData =  accountService.validateOtp(otpNumber);
                    progressDialog.dismiss();
                    if(responseData == null){
                        ActivityUtil.showDialog(this,"Error","Something went wrong.");
                    }else {
                        int code = responseData.code();
                        if(code != 200){
                            ActivityUtil.showDialog(this,"Error",responseData.message());
                        }else {
                            // Login

                        }
                    }
                }else{
                    ActivityUtil.showDialog(this, "No Network", "Check your connection.");
                }
                break;
            case R.id.otp_resend:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

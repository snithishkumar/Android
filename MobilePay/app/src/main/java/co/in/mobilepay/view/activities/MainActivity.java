package co.in.mobilepay.view.activities;

import android.Manifest;
import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import co.in.mobilepay.R;
import co.in.mobilepay.application.MobilePayAnalytics;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.bus.PurchaseListPoster;
import co.in.mobilepay.gcm.GcmRegistrationIntentService;
import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.sync.MobilePaySyncAdapter;
import co.in.mobilepay.service.AccountService;
import co.in.mobilepay.service.impl.AccountServiceImpl;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.util.MobilePayUtil;
import co.in.mobilepay.view.fragments.FragmentsUtil;
import co.in.mobilepay.view.fragments.LoginFragment;
import co.in.mobilepay.view.fragments.MobileFragment;
import co.in.mobilepay.view.fragments.OtpFragment;
import co.in.mobilepay.view.fragments.RegistrationFragment;

public class MainActivity extends NotificationBaseActivity implements RegistrationFragment.MainActivityCallback,OtpFragment.MainActivityCallback,LoginFragment.MainActivityCallback,MobileFragment.MainActivityCallback{


    /* Fragments */
    private RegistrationFragment registrationFragment = null;
    private OtpFragment otpFragment = null;
    private LoginFragment loginFragment = null;
    private MobileFragment mobileFragment = null;
    /* Local variable*/
    private RegisterJson registerJson = null;
    private String mobileNumber;
    private boolean isProfileUpdate = false;
    /* Service Layer */
    private AccountService accountService;

    private boolean isPasswordForget = false;


    int notificationType = 0;
    String  purchaseUuid = null;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

   // private Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check PlayService is Enable or not
        ActivityUtil.checkPlayServices(this);

        // Check whether it called from Profile Update screen
        registerJson =  getIntent().getParcelableExtra("registration");
        isProfileUpdate =  getIntent().getBooleanExtra("isProfileUpdate", false);

         notificationType =  getIntent().getIntExtra("notificationType",1);
         purchaseUuid = getIntent().getStringExtra("purchaseUuid");
        // Initialize required object
        init();
        setContentView(R.layout.activity_main);
        // Show initial fragment
        showFragment();
    }



    /**
     * Initialize service layer
     */
    private void init(){
        try{
           // copyDataBase(this);
            if(accountService == null){
                accountService = new AccountServiceImpl(this);
            }

            // Obtain the shared Tracker instance.
            MobilePayAnalytics application = (MobilePayAnalytics) getApplication();
            /*mTracker = application.getDefaultTracker();
            mTracker.setScreenName("MainActivity");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/

           /* Account account = MobilePaySyncAdapter.getSyncAccount(this);
            ContentResolver.setIsSyncable(account,getString(R.string.auth_type),1);
            ContentResolver.setSyncAutomatically(account, getString(R.string.auth_type), true);
            ContentResolver.addPeriodicSync(account, getString(R.string.auth_type), Bundle.EMPTY, 60);*/

        }catch (Exception e){
            Log.e("Error","Error in init",e);
        }

    }

    /**
     * Initialize and call Fragment to show first screen based on isProfileUpdate and isUserPresent
     * isProfileUpdate - true ( Need to show OTP Screen)
     * isUserPresent -  true (Need to show Login Screen)
     * Otherwise, it will show mobile Number screen
     */
    private void showFragment(){
        // check whether it will called from Profile Update
        if(isProfileUpdate){
            otpFragment = new OtpFragment();
            Bundle bundle = new Bundle();
            bundle.putString("mobileNumber",registerJson.getMobileNumber());
            bundle.putBoolean("isProfileUpdate",isProfileUpdate);
            otpFragment.setArguments(bundle);
            FragmentsUtil.addFragment(this, otpFragment, R.id.main_container);
        }else{
            // Check first time or already registered.
            boolean isUserPresent = accountService.isUserPresent();
            if(isUserPresent){
                ActivityUtil.IS_LOGIN = false;
                loginFragment = new LoginFragment();
                FragmentsUtil.addFragment(this,loginFragment,R.id.main_container);
            }else{
                mobileFragment = new MobileFragment();
                FragmentsUtil.addFragment(this,mobileFragment,R.id.main_container);

            }
        }

    }


    @Override
    public void onResume(){
        super.onResume();
        MobilePayBus.getInstance().register(this);
        // Check PlayService is Enable or not
        ActivityUtil.checkPlayServices(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }






    public AccountService getAccountService() {
        return accountService;
    }

    /**
     * Callback function
     * @param code
     * @param data
     */
    @Override
    public void success(int code, Object data) {

        switch (code){
            case MessageConstant.MOBILE_VERIFY_OK:
                otpFragment = new OtpFragment();
                mobileNumber = (String)data;
                FragmentsUtil.replaceFragment(this,otpFragment,R.id.main_container);
                break;
            case MessageConstant.REG_OK:
            case MessageConstant.PROFILE_UPDATE_SUCCESS:
                ActivityUtil.IS_LOGIN = false;
                loginFragment = new LoginFragment();
                FragmentsUtil.replaceFragment(this,loginFragment,R.id.main_container);
                break;
            case MessageConstant.OTP_OK:
                registrationFragment = new RegistrationFragment();
                FragmentsUtil.replaceFragment(this,registrationFragment,R.id.main_container);
                break;
            case MessageConstant.LOGIN_OK:
                checkPermission();
                ActivityUtil.IS_LOGIN = true;
                if(purchaseUuid != null){
                    callActivity(notificationType,purchaseUuid);
                }else{
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                // Need to call home screen
                break;
            case MessageConstant.LOGIN_INVALID_MOBILE:
                mobileFragment = new MobileFragment();
                FragmentsUtil.addFragment(this,mobileFragment,R.id.main_container);
                break;

            case MessageConstant.FORGET_PASSWORD:
                mobileFragment = new MobileFragment();
                isPasswordForget = true;
                FragmentsUtil.replaceFragmentNoStack(this,mobileFragment,R.id.main_container);
                break;
        }
    }

    private void gcmRegistration(){
        Intent intent = new Intent(this, GcmRegistrationIntentService.class);
        this.startService(intent);
    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
*/

    @Override
    public void onPause() {
        MobilePayBus.getInstance().unregister(this);
        super.onPause();
    }


    @Subscribe
    public void purchaseResponse(PurchaseListPoster purchaseListPoster){
        processResponse(purchaseListPoster);
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public RegisterJson getRegisterJson() {
        return registerJson;
    }

    public boolean isPasswordForget() {
        return isPasswordForget;
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_PHONE_STATE},
                REQUEST_CODE_ASK_PERMISSIONS);
    }


    private void checkPermission() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)) {
                showMessageOKCancel("You need to allow access to call",
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
            gcmRegistration();
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
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
                    gcmRegistration();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Unable to call.Please enable the Phone permission.", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}

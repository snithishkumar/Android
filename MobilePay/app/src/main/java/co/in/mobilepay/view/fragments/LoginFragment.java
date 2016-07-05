package co.in.mobilepay.view.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.AccountServiceImpl;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.MainActivity;

/**
 * Created by Nithish on 07-02-2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    List<String> selectedPin = new ArrayList<>(6);
    Button firstPin = null;
    Button secondPin = null;
    Button thirdPin = null;
    Button fourthPin = null;
    Button fifthPin = null;
    Button sixthPin = null;

    private MainActivity mainActivity;
    private MainActivityCallback mainActivityCallback;
    ProgressDialog progressDialog = null;

    private String token = null;


   public LoginFragment(){

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity)context;
        this.mainActivityCallback = mainActivity;
    }



    private void addValue(String value){
        int count = selectedPin.size();
        if(count < 6){
            selectedPin.add(value);
            resetColor(count + 1, R.drawable.small_round_red);
        }
    }

    private void verifyLogin(){
        int count = selectedPin.size();
        if(count == 6){
            boolean isNet = ServiceUtil.isNetworkConnected(mainActivity);
            if(isNet){
                progressDialog = ActivityUtil.showProgress(getString(R.string.login_submit_heading), getString(R.string.login_submit_message), mainActivity);
                String data = TextUtils.join("",selectedPin);
                mainActivity.getAccountService().login(data);
            }else{
                ActivityUtil.showDialog(mainActivity,getString(R.string.no_network_heading), getString(R.string.no_network));
            }


        }
    }

    @Override
    public void onClick(View v) {
        int count = selectedPin.size();
        if(count == 6){
            verifyLogin();
        }
        switch (v.getId()){
            case R.id.log_zero:
                addValue("0");
                break;
            case R.id.log_one:
                addValue("1");
                break;
            case R.id.log_two:
                addValue("2");
                break;
            case R.id.log_three:
                addValue("3");
                break;
            case R.id.log_four:
                addValue("4");
                break;
            case R.id.log_five:
                addValue("5");
                break;
            case R.id.log_six:
                addValue("6");
                break;
            case R.id.log_seven:
                addValue("7");
                break;
            case R.id.log_eight:
                addValue("8");
                break;
            case R.id.log_nine:
                addValue("9");
                break;
            case R.id.log_del:
                if(count > 0){
                    selectedPin.remove(count - 1);
                    resetColor(count, R.drawable.small_round_background);
                }
                break;

            case R.id.log_forget_pin:
            case MessageConstant.LOGIN_INVALID_MOBILE:
                mainActivityCallback.success(MessageConstant.FORGET_PASSWORD, null);
                break;

        }
        verifyLogin();
    }

    private void reSetPin(){
        int totalCount = selectedPin.size();
        for(int i =totalCount; i > 0; i--){
            selectedPin.remove(i - 1);
            resetColor(i, R.drawable.small_round_background);
        }
    }

    private void init( View view){
       /* try{
            InstanceID instanceID = InstanceID.getInstance(mainActivity);
            token = instanceID.getToken(getString(R.string.google_cloud_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
        }catch (Exception e){
            Log.e("Error","Error  in GoogleCloudMessaging",e);
        }*/
        Button zero = (Button)   view.findViewById(R.id.log_zero);
        Button one = (Button)  view.findViewById(R.id.log_one);
        Button two = (Button)  view.findViewById(R.id.log_two);
        Button three = (Button)  view.findViewById(R.id.log_three);
        Button four = (Button) view.findViewById(R.id.log_four);
        Button five = (Button) view.findViewById(R.id.log_five);
        Button six = (Button) view.findViewById(R.id.log_six);
        Button seven = (Button)  view.findViewById(R.id.log_seven);
        Button eight = (Button) view.findViewById(R.id.log_eight);
        Button nine = (Button) view.findViewById(R.id.log_nine);
        Button delButton = (Button) view.findViewById(R.id.log_del);
        Button forgetButton = (Button) view.findViewById(R.id.log_forget_pin);
        setListener(zero);
        setListener(one);
        setListener(two);
        setListener(three);
        setListener(four);
        setListener(five);
        setListener(six);
        setListener(seven);
        setListener(eight);
        setListener(nine);
        setListener(delButton);
        setListener(forgetButton);


         firstPin = (Button)   view.findViewById(R.id.log_first_value);
         secondPin = (Button)   view.findViewById(R.id.log_second_value);
         thirdPin = (Button)   view.findViewById(R.id.log_third_value);
         fourthPin = (Button)   view.findViewById(R.id.log_fourth_value);
         fifthPin = (Button)   view.findViewById(R.id.log_fifth_value);
         sixthPin = (Button)   view.findViewById(R.id.log_sixth_value);

    }


    private void setListener(Button button){
        button.setOnClickListener(this);
    }



    private void resetColor(int pos,int id){
        switch (pos){
            case 1:
                firstPin.setBackground(ContextCompat.getDrawable(mainActivity,id));
                break;
            case 2:
                secondPin.setBackground(ContextCompat.getDrawable(mainActivity,id));
                break;
            case 3:
                thirdPin.setBackground(ContextCompat.getDrawable(mainActivity,id));
                break;
            case 4:
                fourthPin.setBackground(ContextCompat.getDrawable(mainActivity,id));
                break;
            case 5:
                fifthPin.setBackground(ContextCompat.getDrawable(mainActivity,id));
                break;
            case 6:
                sixthPin.setBackground(ContextCompat.getDrawable(mainActivity,id));
                break;
        }
    }



    @Subscribe
    public void processLoginResponse(ResponseData responseData){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
        switch (responseData.getStatusCode()){
            case MessageConstant.LOGIN_OK:
                mainActivityCallback.success(MessageConstant.LOGIN_OK,null);
                // Need to callback acitivity
                break;
            case MessageConstant.LOGIN_INTERNAL_ERROR:
                ActivityUtil.showDialog(mainActivity,getString(R.string.error),getString(R.string.internal_error));
                break;
            case MessageConstant.LOGIN_INVALID_MOBILE:
                mainActivityCallback.success(MessageConstant.LOGIN_INVALID_MOBILE, null);
                break;

            case MessageConstant.OTP_INVALID:
                break;
            default:

                ActivityUtil.showDialog(mainActivity, getString(R.string.error), getString(R.string.invalid_pin));
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reSetPin();
                    }
                });
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



    public  interface MainActivityCallback {
        void success(int code,Object data);
    }

    @Override
    public void onDestroyView() {
        mainActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        super.onDestroyView();

    }
}

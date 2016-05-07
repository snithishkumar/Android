package co.in.mobilepay.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import co.in.mobilepay.R;
import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.AccountServiceImpl;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.MainActivity;

/**
 * Created by Nithish on 06-02-2016.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener,AccountServiceImpl.AccountServiceCallback{
    EditText name = null;
    EditText password = null;
    EditText rePassword = null;

    private MainActivity mainActivity = null;
    ProgressDialog progressDialog = null;
    private MainActivityCallback mainActivityCallback =null;
    String mobileNumber = null;
    boolean isPasswordForget;

    public RegistrationFragment(){
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity)context;
        this.mainActivityCallback = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mobileNumber = getArguments().getString("mobileNumber");
        isPasswordForget = getArguments().getBoolean("isPasswordForget");
        if(isPasswordForget){
            mainActivity.getAccountService().deleteUser();
        }
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        name = (EditText) view.findViewById(R.id.reg_name);
        password = (EditText)view.findViewById(R.id.reg_password);
        rePassword = (EditText) view.findViewById(R.id.reg_repassword);
        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.reg_submit);
        floatingActionButton.setOnClickListener(this);
        return view;
    }

    public RegisterJson getRegistrationJson(){
        String nameTemp = name.getText().toString();
        if(nameTemp == null){
            name.setError("Name should not be blank");
        }
        String passwordTemp = password.getText().toString();
        if(passwordTemp == null){
            password.setError("Password should not be blank");
        }
        String rePasswordTemp = rePassword.getText().toString();
        if(rePasswordTemp == null){
            rePassword.setError("RePassword should not be blank");
        }
        if(!passwordTemp.equals(rePasswordTemp)){
            rePassword.setError("Password and retype password are mismatched");
        }

        RegisterJson registerJson = new RegisterJson(nameTemp,passwordTemp,mobileNumber,"",isPasswordForget);
        return registerJson;
    }

    @Override
    public void onClick(View v) {
        RegisterJson registerJson = getRegistrationJson();
        if(registerJson != null){
            String imeiNumber = ServiceUtil.getIMEINumber(mainActivity);
            registerJson.setImei(imeiNumber);
            boolean isNet = ServiceUtil.isNetworkConnected(mainActivity);
            if(isNet){
                progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", mainActivity);

                mainActivity.getAccountService().createUser(registerJson,this);
            }else{
                ActivityUtil.showDialog(mainActivity, "No Network", "Check your connection.");
            }
        }
    }


    @Override
    public void accountServiceCallback(int statusCode, Object data) {

        progressDialog.dismiss();
        switch (statusCode){
            case MessageConstant.REG_OK:
                mainActivityCallback.success(MessageConstant.REG_OK,null);
                break;
            case MessageConstant.REG_ERROR_CODE:
                ActivityUtil.showDialog(mainActivity, "Error", MessageConstant.REG_ERROR);
                break;
            default:
                ActivityUtil.showDialog(mainActivity,"Error",(String)data);
                break;
        }
    }

    public  interface MainActivityCallback {
        void success(int code,Object data);
    }
}

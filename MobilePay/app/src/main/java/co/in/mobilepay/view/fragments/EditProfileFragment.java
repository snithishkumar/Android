package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.AccountService;
import co.in.mobilepay.view.activities.MainActivity;
import co.in.mobilepay.view.activities.NaviDrawerActivity;

/**
 * Created by Nithishkumar on 3/26/2016.
 */
public class EditProfileFragment extends Fragment {
    private EditText vMobileNumber;
    private EditText vProfileName;
    private EditText vPassword;

    private AccountService accountService;
    private NaviDrawerActivity naviDrawerActivity;

    public EditProfileFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_update, container, false);
        init(view);
        loadData();
        return view;
    }

    private void  init(View view){
        vMobileNumber = (EditText) view.findViewById(R.id.profile_edit_mobile_no);
        vProfileName = (EditText) view.findViewById(R.id.profile_name);
        vPassword = (EditText) view.findViewById(R.id.profile_edit_pass);
    }

    private void loadData(){
        UserEntity userEntity = accountService.getUserDetails();
        vMobileNumber.setText(userEntity.getMobileNumber());
        vProfileName.setText(userEntity.getName());
        vPassword.setText(".....");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.naviDrawerActivity = (NaviDrawerActivity)context;
        this.accountService = naviDrawerActivity.getAccountService();
        //this.mainActivityCallback = mainActivity;
    }


}

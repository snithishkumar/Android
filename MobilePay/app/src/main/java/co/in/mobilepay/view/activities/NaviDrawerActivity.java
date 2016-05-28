package co.in.mobilepay.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.squareup.otto.Subscribe;

import co.in.mobilepay.R;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.AccountService;
import co.in.mobilepay.service.impl.AccountServiceImpl;
import co.in.mobilepay.view.fragments.EditProfileFragment;
import co.in.mobilepay.view.fragments.FragmentsUtil;
import co.in.mobilepay.view.fragments.SaveCardsFragment;

/**
 * Created by Nithishkumar on 3/27/2016.
 */
public class NaviDrawerActivity extends AppCompatActivity implements EditProfileFragment.EditProfileFragmentCallBack{

    private AccountService accountService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_drawer);
        init();
        int options = getIntent().getIntExtra("options",0);
        showFragment(options);
    }

    private void init(){
        try{
            accountService = new AccountServiceImpl(this);
        }catch (Exception e){
            Log.e("Error","Error in init - NaviDrawerActivity",e);
        }

    }

    private void showFragment(int options){
        switch (options){
            case 1:
               EditProfileFragment editProfileFragment = new EditProfileFragment();
                FragmentsUtil.addFragment(this, editProfileFragment, R.id.navi_drawer_container);
                break;

            case 3:
                SaveCardsFragment saveCardsFragment = new SaveCardsFragment();
                FragmentsUtil.addFragment(this, saveCardsFragment, R.id.navi_drawer_container);
                break;
        }
    }



    public void showNewCardFragment(View view){
        Intent intent = new Intent(this, NewSaveCardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(int option, String mobile) {
        if(option == 1){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("mobileNumber",mobile);
            intent.putExtra("isProfileUpdate",true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            finish();
        }

    }

    public AccountService getAccountService() {
        return accountService;
    }

}

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
import co.in.mobilepay.service.CardService;
import co.in.mobilepay.service.impl.AccountServiceImpl;
import co.in.mobilepay.service.impl.CardServiceImpl;
import co.in.mobilepay.view.fragments.EditProfileFragment;
import co.in.mobilepay.view.fragments.FragmentsUtil;
import co.in.mobilepay.view.fragments.SaveCardsFragment;

/**
 * Created by Nithishkumar on 3/27/2016.
 */
public class NaviDrawerActivity extends AppCompatActivity {

    private AccountService accountService = null;

    private CardService cardService;

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
            cardService = new CardServiceImpl(this);
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

    @Subscribe
    private void processServerResponse(ResponseData responseData){

    }

    public void showNewCardFragment(View view){
        Intent intent = new Intent(this, NewSaveCardActivity.class);
        startActivity(intent);
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public CardService getCardService() {
        return cardService;
    }
}

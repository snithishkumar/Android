package co.in.mobilepay.view.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import co.in.mobilepay.R;
import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.AccountService;
import co.in.mobilepay.service.impl.AccountServiceImpl;
import co.in.mobilepay.view.fragments.AboutAsFragment;
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

            case 4:
                AboutAsFragment aboutAsFragment = new AboutAsFragment();
                FragmentsUtil.addFragment(this, aboutAsFragment, R.id.navi_drawer_container);
                break;


            case 5:
                showPlayStore();
                break;

            case 3:
                SaveCardsFragment saveCardsFragment = new SaveCardsFragment();
                FragmentsUtil.addFragment(this, saveCardsFragment, R.id.navi_drawer_container);
                break;
        }
    }


    private void showPlayStore(){
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
       /* goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT  |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);*/
        try {
            startActivity(goToMarket);
            finish();
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            finish();
        }
    }



    public void showNewCardFragment(View view){
        Intent intent = new Intent(this, NewSaveCardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(int option, RegisterJson registerJson) {
        if(option == 1){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("registration",registerJson);
            intent.putExtra("isProfileUpdate",true);
           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // go to previous activity
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public AccountService getAccountService() {
        return accountService;
    }

}

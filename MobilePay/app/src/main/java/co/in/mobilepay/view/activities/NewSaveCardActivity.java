package co.in.mobilepay.view.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Subscribe;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.json.response.CardJson;
import co.in.mobilepay.json.response.LoginError;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.CardService;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.CardServiceImpl;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.fragments.FragmentsUtil;
import co.in.mobilepay.view.fragments.NewCardFragment;
import co.in.mobilepay.view.fragments.ProductsDetailsFragment;

/**
 * Created by Nithish on 13-03-2016.
 */
public class NewSaveCardActivity extends AppCompatActivity implements NewCardFragment.NewSaveCardActivityCallback{

    private CardService cardService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobilePayBus.getInstance().register(this);
        init();
        setContentView(R.layout.activity_new_save_card);
        showFragment();
    }

    /**
     * Initialize service layer
     */
    private void init() {
        try {
            cardService = new CardServiceImpl(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showFragment(){
        NewCardFragment newCardFragment = new NewCardFragment();
        FragmentsUtil.addFragment(this,newCardFragment,R.id.save_new_card_container);
    }



    @Override
    public void success(int code, Object data) {
        boolean isNet = ServiceUtil.isNetworkConnected(this);
        if(isNet) {
            progressDialog = ActivityUtil.showProgress("In Progress", "Authenticating...", this);
            CardJson cardJson = (CardJson)data;
            cardService.createCard(cardJson);
        }else{
            ActivityUtil.showDialog(this, "No Network", "Check your connection.");
        }
    }

    private void dismissProgress(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Subscribe
    public void saveCardResponse(ResponseData responseData){
        dismissProgress();
        if(responseData.getStatusCode() == MessageConstant.CARD_LIST_FAILURE){
            // Need
            ActivityUtil.showDialog(this, "Error", "Oops something went wrong. Please try later.");
        }else{
            // List View
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Subscribe
    public void loginError(LoginError loginError){
        dismissProgress();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MobilePayBus.getInstance().unregister(this);
    }
}

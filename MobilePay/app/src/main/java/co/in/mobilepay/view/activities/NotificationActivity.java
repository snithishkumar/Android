package co.in.mobilepay.view.activities;

import android.content.Intent;
import android.os.Bundle;


import com.squareup.otto.Subscribe;

import co.in.mobilepay.R;
import co.in.mobilepay.application.MobilePayAnalytics;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.bus.PurchaseListPoster;

/**
 * Created by Nithishkumar on 5/1/2016.
 */
public class NotificationActivity extends NotificationBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
       int notificationType =  getIntent().getIntExtra("notificationType",1);
        String  purchaseUuid = getIntent().getStringExtra("purchaseUuid");
        if(ActivityUtil.IS_LOGIN){
            callActivity(notificationType,purchaseUuid);

        }else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("notificationType",notificationType);
            intent.putExtra("purchaseUuid",purchaseUuid);
            startActivity(intent);
            finish();
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
        MobilePayAnalytics.getInstance().trackScreenView("Notification Screen");
        super.onResume();
    }


    @Subscribe
    public void purchaseResponse(PurchaseListPoster purchaseListPoster){
        processResponse(purchaseListPoster);


    }




}

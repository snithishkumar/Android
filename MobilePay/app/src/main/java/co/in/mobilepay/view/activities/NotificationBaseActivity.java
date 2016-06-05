package co.in.mobilepay.view.activities;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.otto.Subscribe;

import java.sql.SQLException;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.bus.PurchaseListPoster;
import co.in.mobilepay.dao.NotificationDao;
import co.in.mobilepay.dao.PurchaseDao;
import co.in.mobilepay.dao.impl.NotificationDaoImpl;
import co.in.mobilepay.dao.impl.PurchaseDaoImpl;
import co.in.mobilepay.entity.NotificationEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.enumeration.NotificationType;
import co.in.mobilepay.enumeration.OrderStatus;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.sync.MobilePaySyncAdapter;

/**
 * Created by Nithishkumar on 5/1/2016.
 */
public class NotificationBaseActivity extends AppCompatActivity {

    ProgressDialog progressDialog = null;
    AppCompatActivity context = null;
    PurchaseDao purchaseDao = null;
    String purchaseUuid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    /**
     * There is an possible, user may made payment or oder will be update without opening notification.So update that corresponding status
     * @param purchaseUuid
     * @param notificationDao
     * @param notificationType
     * @throws SQLException
     */
    private int getNotificationType(String purchaseUuid,NotificationDao notificationDao,int notificationType)throws SQLException{
        PurchaseEntity purchaseEntity =  purchaseDao.getPurchaseEntity(purchaseUuid);
        if(purchaseEntity != null){
            if(notificationType == NotificationType.STATUS.getNotificationType()){
                if(purchaseEntity.getOrderStatus() != null){
                    switch (purchaseEntity.getOrderStatus()){
                        case CANCELED:
                        case DELIVERED:
                            return NotificationType.CANCEL.getNotificationType();
                    }
                }
            }else if(notificationType == NotificationType.PURCHASE.getNotificationType()){
                if(purchaseEntity.getOrderStatus() != null){
                    switch (purchaseEntity.getOrderStatus()){
                        case PACKING:
                        case FAILED_TO_DELIVER:
                        case OUT_FOR_DELIVERY:
                        case READY_TO_COLLECT:
                        case READY_TO_SHIPPING:
                            return NotificationType.STATUS.getNotificationType();
                    }
                }
            }

        }
        return notificationType;
    }


    protected void callActivity(int notificationType,String purchaseUuid){
        try{
            this.purchaseUuid = purchaseUuid;
            NotificationDao notificationDao = new NotificationDaoImpl(this);
            purchaseDao = new PurchaseDaoImpl(this);
            Log.i("Noti","ActivityUtil.IS_LOGIN"+ActivityUtil.IS_LOGIN);
            // Check User is already login or not. If its login, then call corresponding activity
            if(ActivityUtil.IS_LOGIN){
             int currentNotificationType =   getNotificationType(purchaseUuid,notificationDao,notificationType);

                // Check given notification is  Order_Status. If its Order_Status, then call HomeActivity and navigate to Order status tab.
                if(currentNotificationType == NotificationType.STATUS.getNotificationType()){

                    // Clear notification data from Database
                    notificationDao.clearNotification(NotificationType.STATUS.getNotificationType(notificationType));
                    // Call HomeActivity. 1 indicates Order_status tab
                    callHomeActivity(1);
                    return;
                    //Check given notification is Purchase. If its purchase, then check only one purchase data or group of purchase data and call according
                }else if(currentNotificationType == NotificationType.PURCHASE.getNotificationType()){
                    // Get Purchase count.
                    long count =  notificationDao.getNotificationCount(NotificationType.PURCHASE);
                    // Clear notification data from Database
                    notificationDao.clearNotification(NotificationType.STATUS.getNotificationType(notificationType));
                    // If its more than one, then call HomeActivity
                    if(count > 1){
                        callHomeActivity(0);
                    }else{// Otherwise call PurchaseDetails Activity
                        callPurchaseDetails(purchaseUuid,1); // 1 - PURCHASE_LIST
                    }
                    return;
                }else{  //Check given notification is Cancel. If its Cancel, then check only one Cancel data or group of Cancel data and call according
                    // Get Cancel count.
                    long count =  notificationDao.getNotificationCount(NotificationType.CANCEL);
                    // Clear notification data from Database
                    notificationDao.clearNotification(NotificationType.CANCEL);
                    // If its more than one, then call HomeActivity
                    if(count > 1){
                        callHomeActivity(2);
                    }else{ // Otherwise call PurchaseDetails Activity
                        callPurchaseDetails(purchaseUuid,3); // 3 - PURCHASE_HISTORY_LIST
                    }
                    return;
                }
            }else {

            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * Call the HomeActivity
     * @param tabPosition
     */
    private void callHomeActivity(int tabPosition){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("tabPosition",tabPosition);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Call PurchaseDetails activity. If Purchase UUId is not present in Purchase, Sync that purchase
     * @param purchaseUuid
     * @param fragmentOptions
     */
    private void callPurchaseDetails(String purchaseUuid,int fragmentOptions){
        try{
            PurchaseEntity purchaseEntity = purchaseDao.getPurchaseEntity(purchaseUuid);
            if(purchaseEntity == null){
                syncData(purchaseUuid);
            }else{
                callPurchaseDetailsActivity(purchaseEntity.getPurchaseId(),fragmentOptions);
            }
        }catch (Exception e){
            e.printStackTrace();
            callHomeActivity(0); // If any Error happened, then call HomeActivity
        }

    }


    private void callPurchaseDetailsActivity(int purchaseId,int fragmentOptions){
        Intent intent = new Intent(this, PurchaseDetailsActivity.class);
        intent.putExtra("purchaseId",purchaseId);
        intent.putExtra("fragmentOptions",fragmentOptions);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    /**
     * Sync Purchase Data
     * @param purchaseUuid
     */
    private void syncData(String purchaseUuid){
        boolean isNet = ServiceUtil.isNetworkConnected(this);
        if(isNet){
            progressDialog = ActivityUtil.showProgress("In Progress", "Please wait...", this);
            Account account = MobilePaySyncAdapter.getSyncAccount(this);

            Bundle settingsBundle = new Bundle();
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_MANUAL, true);
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            settingsBundle.putInt("currentTab",6);
            settingsBundle.putString("purchaseUuid",purchaseUuid);
            ContentResolver.requestSync(account, getString(R.string.auth_type), settingsBundle);
        }else{
            context = this;
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            // Setting Dialog Title
            alertDialog.setTitle("No Network");

            // Setting Dialog Message
            alertDialog.setMessage("Check your connection.");

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                    context.finish();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }

    }



    @Override
    public void onPause() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }

        super.onPause();
    }

    protected void processResponse(PurchaseListPoster purchaseListPoster){
        progressDialog.dismiss();
        if(purchaseListPoster.getStatusCode() == 200) {
            try{
                PurchaseEntity purchaseEntity = purchaseDao.getPurchaseEntity(purchaseUuid);
                int fragmentOptions = 1;
                if(purchaseEntity.getOrderStatus() != null &&(purchaseEntity.getOrderStatus().equals(OrderStatus.CANCELED.toString()) || purchaseEntity.getOrderStatus().equals(OrderStatus.DELIVERED.toString()))){
                    fragmentOptions = 3;
                }
                callPurchaseDetailsActivity(purchaseEntity.getPurchaseId(),fragmentOptions);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            ActivityUtil.toast(this,getString(R.string.error_purchase_list));
            callHomeActivity(0);
        }
    }


}

package co.in.mobilepay.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.sql.SQLException;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.dao.PurchaseDao;
import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.dao.impl.PurchaseDaoImpl;
import co.in.mobilepay.dao.impl.UserDaoImpl;
import co.in.mobilepay.entity.MerchantEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.json.response.PurchaseJson;
import co.in.mobilepay.json.response.ResponseData;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Nithish on 05-03-2016.
 */
public class MobilePaySyncAdapter extends AbstractThreadedSyncAdapter {


    public final String LOG_TAG = MobilePaySyncAdapter.class.getSimpleName();

    private PurchaseDao purchaseDao;
    private UserDao userDao;
    private MobilePayAPI mobilePayAPI;
    private Gson gson;

    public MobilePaySyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        try{
            purchaseDao = new PurchaseDaoImpl(context);
            userDao = new UserDaoImpl(context);
            mobilePayAPI = ServiceAPI.INSTANCE.getMobilePayAPI();
            gson = new Gson();
        }catch (Exception e){
            e.printStackTrace();
        }


       // android.os.Debug.waitForDebugger();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.i(LOG_TAG, "onPerformSync Called.");
        //syncPurchaseData();
    }


    public void syncPurchaseData(){
        try {
            long serverTime = purchaseDao.getMostRecentPurchaseServerTime();
            UserEntity userEntity = userDao.getUser();
            JsonObject requestData = new JsonObject();
            requestData.addProperty("serverToken",userEntity.getServerToken());
            requestData.addProperty("clientToken",userEntity.getAccessToken());
            requestData.addProperty("serverTime", serverTime);
            Call<ResponseData> responseDataCall = mobilePayAPI.syncPurchaseData(requestData);
            Response<ResponseData> dataResponse =  responseDataCall.execute();
            ResponseData responseData = dataResponse.body();
            int statusCode = responseData.getStatusCode();
            String purchaseDetails = responseData.getData();
            List<PurchaseJson> purchaseJsonList =     gson.fromJson(purchaseDetails, new TypeToken<List<PurchaseJson>>() {
            }.getType());
            for(PurchaseJson purchaseJson : purchaseJsonList){
                try{
                    processPurchaseJson(purchaseJson);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            // responseDataCall.
        }catch (Exception e){
            Log.e("Error","Error in  syncPurchaseData",e);
        }
    }


    private void processPurchaseJson(PurchaseJson purchaseJson )throws SQLException{
        MerchantEntity  merchantEntity = purchaseDao.getMerchantEntity(purchaseJson.getMerchants().getMerchantUuid());
        if(merchantEntity == null){
            merchantEntity = new MerchantEntity(purchaseJson.getMerchants());
            purchaseDao.createMerchantEntity(merchantEntity);
        }else if(merchantEntity.getLastModifiedDateTime() < purchaseJson.getMerchants().getLastModifiedDateTime()){
            merchantEntity.toClone(purchaseJson.getMerchants());
            purchaseDao.updateMerchantEntity(merchantEntity);
            // Need to update
        }
        UserEntity dbUserEntity = userDao.getUser(purchaseJson.getUsers().getMobileNumber());
        PurchaseEntity purchaseEntity = purchaseDao.getPurchaseEntity(purchaseJson.getPurchaseId());

        if(purchaseEntity != null){
            purchaseEntity.toClone(purchaseJson);
            purchaseDao.updatePurchase(purchaseEntity);
            // Update
        }else{
            purchaseEntity = new PurchaseEntity(purchaseJson);
            purchaseEntity.setMerchantEntity(merchantEntity);
            purchaseEntity.setUserEntity(dbUserEntity);
            purchaseDao.createPurchase(purchaseEntity);
        }
    }


    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.auth_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

        }
        return newAccount;
    }
}

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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.bus.PurchaseListPoster;
import co.in.mobilepay.dao.NotificationDao;
import co.in.mobilepay.dao.PurchaseDao;
import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.dao.impl.NotificationDaoImpl;
import co.in.mobilepay.dao.impl.PurchaseDaoImpl;
import co.in.mobilepay.dao.impl.UserDaoImpl;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.entity.CounterDetailsEntity;
import co.in.mobilepay.entity.DiscardEntity;
import co.in.mobilepay.entity.MerchantEntity;
import co.in.mobilepay.entity.NotificationEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.entity.TransactionalDetailsEntity;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.enumeration.NotificationType;
import co.in.mobilepay.enumeration.OrderStatus;
import co.in.mobilepay.json.response.AddressBookJson;
import co.in.mobilepay.json.response.AddressJson;
import co.in.mobilepay.json.response.CounterDetailsJson;
import co.in.mobilepay.json.response.DiscardJson;
import co.in.mobilepay.json.response.DiscardJsonList;
import co.in.mobilepay.json.response.GetPurchaseDetailsList;
import co.in.mobilepay.json.response.LuggageJson;
import co.in.mobilepay.json.response.OrderStatusJsonsList;
import co.in.mobilepay.json.response.PayedPurchaseDetailsJson;
import co.in.mobilepay.json.response.PayedPurchaseDetailsList;
import co.in.mobilepay.json.response.PurchaseJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.json.response.TokenJson;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.MessageConstant;
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
    private NotificationDao notificationDao;

    ExecutorService executorService = Executors.newFixedThreadPool(5);
    CountDownLatch countDownLatch = new CountDownLatch(4);



    public MobilePaySyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        init(context);

    }

    /**
     * Initialize DAO's and Gson
     * @param context
     */
    private void init(Context context){
        try{
            purchaseDao = new PurchaseDaoImpl(context);
            userDao = new UserDaoImpl(context);
            mobilePayAPI = ServiceAPI.INSTANCE.getMobilePayAPI();
            gson = new Gson();
            notificationDao = new NotificationDaoImpl(context);
        }catch (Exception e){
            Log.e(LOG_TAG,"Error in MobilePaySyncAdapter",e);
        }
    }

    /**
     * Common Sync Location. Call Corresponding Sync method based on flag
     * @param account
     * @param extras
     * @param authority
     * @param provider
     * @param syncResult
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
       try{
           countDownLatch = new CountDownLatch(4);
           executorService.execute(new Runnable() {
               @Override
               public void run() {
                   sendUnSyncedDataSynchronize();
                   countDownLatch.countDown();
               }
           });

           executorService.execute(new Runnable() {
               @Override
               public void run() {
                   syncPurchaseData();
                   countDownLatch.countDown();
               }
           });

           executorService.execute(new Runnable() {
               @Override
               public void run() {
                   syncOrderStatus();
                   countDownLatch.countDown();
               }
           });

           executorService.execute(new Runnable() {
               @Override
               public void run() {
                   syncPurchaseHistoryData();
                   countDownLatch.countDown();
               }
           });
           countDownLatch.await();
           System.out.println("sdfasdfasdf");
       }catch (Exception e){
           e.printStackTrace();
       }
        System.out.println("sdfasdfasdf");


    }

    @Deprecated
    public void onPerformSync_old(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.i(LOG_TAG, "onPerformSync Called.");

        // 1 - Purchase List, 2- Order Status List, 3 - Purchase History List,4- SyncUnSyncedData,5- Call Purchase List,Order Status List and Purchase History List all at once
       int currentTab =  extras.getInt("currentTab",0);
        switch (currentTab){
            case 1:
                // Send If any UnSynced Data
                sendUnSyncedDataAsync();
                // Get Current Purchase List
                syncPurchaseData();
                syncOrderStatus();
                syncPurchaseHistoryData();
                break;
            case 2:
                // Send If any UnSynced Data
                sendUnSyncedDataAsync();
                // Get Order Status
                syncOrderStatus();
                break;
            case 3:
                // Send If any UnSynced Data
                sendUnSyncedDataAsync();
                // Get Purchase History List
                syncPurchaseHistoryData();
                break;

            case 4:
                sendUnSyncedDataSynchronize();
                break;

            case 5:
                syncPurchaseData();
                syncOrderStatus();
                syncPurchaseHistoryData();
                break;

            case 6:
                String purchaseUuid =  extras.getString("purchaseUuid");
                getPurchaseDetailsData(purchaseUuid);
                break;
        }

    }

    /**
     * Send UnSynced Data to the server
     */
    private void sendUnSyncedDataSynchronize(){
        syncUserDeliveryAddress();
        sendUnSyncDeclineData();
        sendUnSyncPayedData();
    }

    /**
     * Send UnSynced Data to the server. Each Process Runs Separate Thread
     */
    private void sendUnSyncedDataAsync(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                syncUserDeliveryAddress();
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                sendUnSyncDeclineData();
            }
        });

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                sendUnSyncPayedData();
            }
        });
    }

    /**
     * Get Current User  and App Token
     * @return
     * @throws SQLException
     */
    private JsonObject userRequest()throws SQLException{
        UserEntity userEntity = userDao.getUser();
        JsonObject requestData = new JsonObject();
        requestData.addProperty("serverToken", userEntity.getServerToken());
        requestData.addProperty("accessToken", userEntity.getAccessToken());
        return requestData;
    }


    private void getPurchaseDetailsData(String purchaseUuid){
        try {
           List<String>  purchaseUuids = new ArrayList<String>();
            purchaseUuids.add(purchaseUuid);
            syncPurchaseDetails(purchaseUuids);
            PurchaseListPoster purchaseListPoster = new PurchaseListPoster();
            purchaseListPoster.setStatusCode(200);
            // Post Success message to Notification
            MobilePayBus.getInstance().post(purchaseListPoster);
            return;
        }catch (Exception e){
            Log.e(LOG_TAG,"Error in getPurchaseDetailsData",e);
        }
        //Send Error as Internal Error
        postErrorCode(MessageConstant.REG_ERROR_CODE);
    }


    /**
     * Get Current User  and App Token
     * @return
     * @throws SQLException
     */
    private void userRequest(TokenJson tokenJson)throws SQLException{
        UserEntity userEntity = userDao.getUser();
        tokenJson.setAccessToken(userEntity.getAccessToken());
        tokenJson.setServerToken(userEntity.getServerToken());
    }


    /**
     * Get Current Purchase List from the server
     */
    private void syncPurchaseData(){
        try {
            // Get User and App Token
            TokenJson tokenJson = new TokenJson();
            userRequest(tokenJson);

            // Sync Request. It Returns Current Purchase UUIDs
            Call<ResponseData> responseDataCall = mobilePayAPI.syncPurchaseListFromServer();

            // Server Response
            Response<ResponseData> dataResponse =  responseDataCall.execute();
            ResponseData responseData = dataResponse.body();

            int statusCode = responseData.getStatusCode();
            // Check the Status code, If its success or failure
            if(statusCode == 300){
                // Process Server Response
                String purchaseUUIDs = responseData.getData();
                // Convert UUIDs to List
                List<String> purchaseJsonList =     gson.fromJson(purchaseUUIDs, new TypeToken<List<String>>() {
                }.getType());
                // Process Each UUIDs
                processPurchaseUUIDs(purchaseJsonList);
                // Once Process, Completed Need to Update List View
                PurchaseListPoster purchaseListPoster = new PurchaseListPoster();
                purchaseListPoster.setStatusCode(200);
                // Post Success message to List View
                MobilePayBus.getInstance().post(purchaseListPoster);
                return;
            }else{
                // In the case of failure, Send Failure message to the server.
                postErrorCode(statusCode);
            }

        }catch (Exception e){
            Log.e("Error","Error in  syncPurchaseListFromServer",e);
            // -- TODO Need to Say Something wrong in mobile side
        }
        //Send Error as Internal Error
        postErrorCode(MessageConstant.REG_ERROR_CODE);
    }

    /**
     * Post Error Message
     * @param errorCode
     */
    private void postErrorCode(int errorCode){
        PurchaseListPoster purchaseListPoster = new PurchaseListPoster();
        purchaseListPoster.setStatusCode(errorCode);
        MobilePayBus.getInstance().post(purchaseListPoster);
    }


    /**
     * Check given purchaseUUIDs with Database. If its not present, then download purchase Details. And also, PurchaseUUID present in local db and that is not in server. Then need to get updates for that PurchaseUUID
     * @param purchaseUUIDs
     * @throws Exception
     */
    private void processPurchaseUUIDs(List<String> purchaseUUIDs)throws  Exception{
       if(purchaseUUIDs.size() > 0){
           // Get Purchase UUID from Database
           List<String> dbPurchaseUUIDs = purchaseDao.getPurchaseUUIDs();
           if(dbPurchaseUUIDs.size() > 0){
               List<String> purchaseJsonListTemp = new ArrayList<>(purchaseUUIDs);
              // Need to Download
               purchaseUUIDs.removeAll(dbPurchaseUUIDs);
               // Need to Get Update.
               dbPurchaseUUIDs.removeAll(purchaseJsonListTemp);
               // If dbPurchaseUUIDs contains data. We must get updates
               if(dbPurchaseUUIDs.size() > 0){
                   // Get Purchase Details
                   syncPurchaseDetailsList(dbPurchaseUUIDs);
               }

           }
           // Get Purchase Details. It process one by one. Bsc to process slow internet
           if(purchaseUUIDs.size() > 0){
               syncPurchaseDetails(purchaseUUIDs);
           }


       }

    }

    /**
     * Get Purchase Details. It process one by one
     * @param purchaseUUIDs
     * @throws SQLException
     */
    private void syncPurchaseDetails(List<String> purchaseUUIDs)throws SQLException{
       List<String> nonSyncPurchaseUUIDs = purchaseDao.getPurchaseUUIDs(purchaseUUIDs);
        if(nonSyncPurchaseUUIDs.size() > 0){
            purchaseUUIDs.removeAll(nonSyncPurchaseUUIDs);
        }
        GetPurchaseDetailsList getPurchaseDetailsList = new GetPurchaseDetailsList();
        userRequest(getPurchaseDetailsList);
        do{
            try{
                getPurchaseDetailsList.getPurchaseUUIDs().clear();
                getPurchaseDetailsList.getPurchaseUUIDs().add(purchaseUUIDs.get(0));
                purchaseUUIDs.remove(0);
                Call<ResponseData> responseDataCall =   mobilePayAPI.syncPurchaseDetailsData(getPurchaseDetailsList);

                // Server Response
                Response<ResponseData> dataResponse =  responseDataCall.execute();
                ResponseData responseData = dataResponse.body();

                int statusCode = responseData.getStatusCode();
                // Check the Status code, If its success or failure
                if(statusCode == 300){
                    String purchaseDetails = responseData.getData();
                    List<PurchaseJson> purchaseJsonList =     gson.fromJson(purchaseDetails, new TypeToken<List<PurchaseJson>>() {
                    }.getType());
                    processPurchaseJson(purchaseJsonList);
                }

            }catch (Exception e){
                e.printStackTrace();
                Log.e(LOG_TAG,"Error in syncPurchaseDetails",e);
            }


        }while (purchaseUUIDs.size() > 0);
    }

    /**
     * Get Purchase Details. It sends all UUIDs once
     * @param purchaseUUIDs
     * @throws SQLException
     */
    private void syncPurchaseDetailsList(List<String> purchaseUUIDs)throws Exception{
        GetPurchaseDetailsList getPurchaseDetailsList = new GetPurchaseDetailsList();
        // Get User Token
        userRequest(getPurchaseDetailsList);

        getPurchaseDetailsList.getPurchaseUUIDs().addAll(purchaseUUIDs);
        // Sync Request
        Call<ResponseData> responseDataCall =   mobilePayAPI.syncPurchaseDetailsData(getPurchaseDetailsList);

        // Server Response
        Response<ResponseData> dataResponse =  responseDataCall.execute();
        ResponseData responseData = dataResponse.body();

        int statusCode = responseData.getStatusCode();

        // Check the Status code, If its success or failure
        if(statusCode == 300){
            // Process Server Response
            String purchaseDetails = responseData.getData();
            List<PurchaseJson> purchaseJsonList = gson.fromJson(purchaseDetails, new TypeToken<List<PurchaseJson>>() {
            }.getType());
            // Process Purchase Details Json
            processPurchaseJson(purchaseJsonList);
        }

    }




    /**
     * Process  PurchaseJson. It checks whether purchase data is already present or not. If it's present, it will update or it will create
     * @param purchaseJsonList
     *
     */
    private void processPurchaseJson(List<PurchaseJson> purchaseJsonList){
        //Save or update Each Purchase details
        for(PurchaseJson purchaseJson : purchaseJsonList){
            try{
                // Check given Merchant details present or not
                MerchantEntity  merchantEntity = purchaseDao.getMerchantEntity(purchaseJson.getMerchants().getMerchantUuid());
                // If its not present create New.Otherwise, it will update
                if(merchantEntity == null){
                    merchantEntity = new MerchantEntity(purchaseJson.getMerchants());
                    purchaseDao.createMerchantEntity(merchantEntity);
                }else if(merchantEntity.getLastModifiedDateTime() < purchaseJson.getMerchants().getLastModifiedDateTime()){
                    merchantEntity.toClone(purchaseJson.getMerchants());
                    purchaseDao.updateMerchantEntity(merchantEntity);
                    // Need to update
                }


                UserEntity dbUserEntity = userDao.getUser(purchaseJson.getUsers().getMobileNumber());
                //Check given Purchase details is already present or not.
                PurchaseEntity purchaseEntity = purchaseDao.getPurchaseEntity(purchaseJson.getPurchaseId());
                //If its not present, it will create new one. Otherwise, it will update
                if(purchaseEntity != null){
                    if(purchaseEntity.getServerDateTime() < purchaseJson.getServerDateTime() && purchaseEntity.isSync()){
                        purchaseEntity.toClone(purchaseJson);
                        processAddressJson(purchaseJson, purchaseEntity);
                        processDiscardJson(purchaseJson,purchaseEntity);
                        purchaseDao.updatePurchase(purchaseEntity);
                    }

                }else{
                    purchaseEntity = new PurchaseEntity(purchaseJson);
                    purchaseEntity.setMerchantEntity(merchantEntity);
                    purchaseEntity.setUserEntity(dbUserEntity);
                    processAddressJson(purchaseJson, purchaseEntity);
                    processDiscardJson(purchaseJson,purchaseEntity);
                    purchaseDao.createPurchase(purchaseEntity);
                }
                createCounterDetails(purchaseJson.getCounterDetails(),purchaseEntity);
            }catch (Exception e){
                Log.e(LOG_TAG, "Error while processing purchase Details. Raw data["+purchaseJson+"]", e);
            }

        }

    }

    /**
     * Check that address is present in DB or not.
     * @param purchaseJson
     * @param purchaseEntity
     * @throws SQLException
     */
    private void processAddressJson(PurchaseJson purchaseJson,PurchaseEntity purchaseEntity)throws SQLException{
        // -- It's an rare scenario.
        AddressJson addressJson =  purchaseJson.getAddressJson();
        if(addressJson != null){
            AddressEntity addressEntity =  userDao.getAddressEntity(addressJson.getAddressUUID());
            if(addressEntity == null){
                addressEntity = new AddressEntity(addressJson);
                addressEntity.setIsSynced(true);
                userDao.saveAddress(addressEntity);
                purchaseEntity.setAddressEntity(addressEntity);
            }

        }
    }

    /**
     * Process Discard Details
     * @param purchaseJson
     * @param purchaseEntity
     * @throws SQLException
     */
    private void processDiscardJson(PurchaseJson purchaseJson,PurchaseEntity purchaseEntity)throws SQLException{
        DiscardJson discardJson =  purchaseJson.getDiscardJson();
        if(discardJson != null){
            DiscardEntity discardEntity = new DiscardEntity(discardJson);
            discardEntity.setPurchaseEntity(purchaseEntity);
            discardEntity.setCreatedDateTime(purchaseEntity.getLastModifiedDateTime());
            purchaseDao.createDiscardEntity(discardEntity);
        }
    }

    /**
     * Get Order status (NOT_YET_SHIPPING or PACKING or OUT_FOR_DELIVERY or Counter Id) from the server
     */
    private void syncOrderStatus(){
        try{
            // Get User and App Token
            JsonObject requestData =  userRequest();

            /**
             *  Get Most Recent  and First Luggage List Server time.Server send back based on this time.
             *  If time is -1 ,then server sends all the Luggage list.
             */
            long startTime = purchaseDao.getLeastLuggageServerTime();
            long endTime = purchaseDao.getMostRecentLuggageServerTime();
            requestData.addProperty("startTime",startTime);
            requestData.addProperty("endTime", endTime);
            // Sync Request
            Call<ResponseData> responseDataCall = mobilePayAPI.syncOrderStatus(requestData);
            // Server Response
            Response<ResponseData> dataResponse =  responseDataCall.execute();
            ResponseData responseData = dataResponse.body();

            // If any purchase UUIDs missed in local Database. Then need to get full details
            List<String> purchaseUUIDs = new ArrayList<>();

            int statusCode = responseData.getStatusCode();
            // Check the Status code, If its success or failure
            if(statusCode == 300){
                // Json to Object
                OrderStatusJsonsList orderStatusJsonsList =  gson.fromJson(responseData.getData(), OrderStatusJsonsList.class);
                // Update Order Status only. Other details (Purchase data) are already present
                List<LuggageJson>  luggageJsonList =  orderStatusJsonsList.getLuggageJsons();

                for(LuggageJson luggageJson : luggageJsonList){
                    //Check given Purchase details is already present or not.
                    PurchaseEntity purchaseEntity = purchaseDao.getPurchaseEntity(luggageJson.getPurchaseGuid());
                   // If purchaseEntity is Present, then update Order status (NOT_YET_SHIPPING or PACKING or OUT_FOR_DELIVERY or Counter Id)
                   if(purchaseEntity != null){
                       purchaseEntity.setLastModifiedDateTime(luggageJson.getUpdatedDateTime());
                       purchaseEntity.setServerDateTime(luggageJson.getServerDateTime());
                       purchaseEntity.setOrderStatus(luggageJson.getOrderStatus());
                       purchaseDao.updatePurchase(purchaseEntity);

                     /*  NotificationEntity notificationEntity = notificationDao.getNotificationEntity(purchaseEntity.getPurchaseGuid());
                       if(notificationEntity != null){
                           notificationEntity.setNotificationType(NotificationType.STATUS);
                           notificationDao.updateNotification(notificationEntity);
                       }*/

                    }else{ // If purchaseEntity is not present, then need to get purchase details data
                       purchaseUUIDs.add(luggageJson.getPurchaseGuid());
                   }
                    // Process Counter Details
                    if(luggageJson.getCounterDetails() != null){
                        createCounterDetails(luggageJson.getCounterDetails(),purchaseEntity);
                    }

                }
                // If purchaseEntity is not present, then Create New Purchase Record
                List<PurchaseJson> purchaseJsonList =   orderStatusJsonsList.getPurchaseJsons();
                processPurchaseJson(purchaseJsonList);
                // Sometimes, There may be possible, PurchaseUUIDs missed in between order status time. So we need to process that missed data.It it not possible, but we need to do
                if(purchaseUUIDs.size() > 0){
                    syncPurchaseDetailsList(purchaseUUIDs);
                }

                // Once Process, Completed Need to Update List View
                PurchaseListPoster purchaseListPoster  = new PurchaseListPoster();
                purchaseListPoster.setStatusCode(200);
                // Success Post
                MobilePayBus.getInstance().post(purchaseListPoster);
                return;

            }else{  // Error Post
               postErrorCode(statusCode);
            }

        }catch (Exception e){
            Log.e(LOG_TAG,"Error in syncOrderStatus",e);
            // -- TODO Need to Say Something wrong in mobile side
        }
        // Error Post
        postErrorCode(MessageConstant.REG_ERROR_CODE);
    }


    /**
     * Create Counter Details Entity
     * @param counterDetails
     * @param purchaseEntity
     * @throws SQLException
     */
    private void createCounterDetails(CounterDetailsJson counterDetails,PurchaseEntity purchaseEntity)throws SQLException{
        if(purchaseEntity.getOrderStatus().toString().equals(OrderStatus.READY_TO_COLLECT.toString())){
            CounterDetailsEntity counterDetailsEntity = purchaseDao.getCounterDetailsEntity(purchaseEntity);
            if(counterDetailsEntity == null){
                counterDetailsEntity = new CounterDetailsEntity(counterDetails);
                purchaseDao.createCounterDetails(counterDetailsEntity);
            }
        }
    }


    /**
     * Get Current Purchase List from the server
     */
    private void syncPurchaseHistoryData(){
        try {
            // Get User and App Token
         TokenJson tokenJson = new TokenJson();
            userRequest(tokenJson);


            // Sync Request
            Call<ResponseData> responseDataCall = mobilePayAPI.syncPurchaseHistoryList();
            // Server Response
            Response<ResponseData> dataResponse =  responseDataCall.execute();
            ResponseData responseData = dataResponse.body();

            int statusCode = responseData.getStatusCode();
            // Check the Status code, If its success or failure
            if(statusCode == 300){
                // Process Server Response
                String purchaseDetails = responseData.getData();
                List<String> purchaseHistoryList = gson.fromJson(purchaseDetails, new TypeToken<List<String>>() {
                }.getType());

                // Get PurchaseHistory UUID from Database
                List<String> dBPurchaseHistoryUUIDs = purchaseDao.getPurchaseHistoryUUIDs();

                if(dBPurchaseHistoryUUIDs.size() > 0){
                    // Need to Download
                    purchaseHistoryList.removeAll(dBPurchaseHistoryUUIDs);


                }
                if(purchaseHistoryList.size() > 0){
                    syncPurchaseDetailsList(purchaseHistoryList);
                }


                // Once Process, Completed Need to Update List View
                PurchaseListPoster purchaseListPoster  = new PurchaseListPoster();
                purchaseListPoster.setStatusCode(200);
                // Success Post
                MobilePayBus.getInstance().post(purchaseListPoster);
                return;
            }else{
                postErrorCode(statusCode);
            }

        }catch (Exception e){
            Log.e(LOG_TAG,"Error in  syncPurchaseListFromServer",e);
        }
        // Error Post
        postErrorCode(MessageConstant.REG_ERROR_CODE);
    }





    /**
     * Sync User Delivery Address
     */
    private void syncUserDeliveryAddress(){
        try{
            // Send Un synced Address
            List<AddressEntity> addressEntityList =  userDao.getUnSyncedAddress();

            // No need call sync bcs address book is empty
            if(addressEntityList.size() < 1){
                return;
            }

            AddressBookJson requestAddressBook = new AddressBookJson();
            // Get User and App Token
            userRequest(requestAddressBook);
            // Get Most Recent Synced Delivery address
            long lastModifiedTime =   userDao.getLastModifiedTime();
            requestAddressBook.setLastModifiedTime(lastModifiedTime);

            for(AddressEntity addressEntity : addressEntityList){
                AddressJson addressJson = new AddressJson(addressEntity);
                requestAddressBook.getAddressList().add(addressJson);
            }

            // Sync Request
            Call<ResponseData> responseDataCall =  mobilePayAPI.syncUserDeliveryAddress(requestAddressBook);

            // Server Response
            Response<ResponseData> dataResponse =  responseDataCall.execute();
            ResponseData responseData = dataResponse.body();

            int statusCode = responseData.getStatusCode();

            // Check the Status code, If its success or failure
            if(statusCode == 200){
                // Process Server Response
                String addressDetails = responseData.getData();
                // Json to Object
                AddressBookJson addressBookJson =  gson.fromJson(addressDetails, AddressBookJson.class);
                List<AddressJson> addressList  =addressBookJson.getAddressList();
                /**
                 * Check address is already present in DB or not. If not present, then create new record. Suppose, if its present then check last modified time
                 */
                for(AddressJson addressJson : addressList){
                   AddressEntity dbAddressEntity =  userDao.getAddressEntity(addressJson.getAddressUUID());
                    if(dbAddressEntity == null){
                        dbAddressEntity = new AddressEntity(addressJson);
                        dbAddressEntity.setAddressUUID(ServiceUtil.generateUUID());
                        userDao.saveAddress(dbAddressEntity);
                    }else if(dbAddressEntity.getLastModifiedTime() < addressJson.getLastModifiedTime()){
                        dbAddressEntity.toAddressEntity(addressJson);
                        userDao.updateAddress(dbAddressEntity);
                    }
                }

            }

        }catch (Exception e){
            Log.e(LOG_TAG,"Error in getUserDeliveryAddress",e);
        }

    }

// -- TODO Address Edit. Need to handle in server side
    public void sendUnSyncPayedData(){
        try{
            // Get UnSynced Payed Data
            List<PurchaseEntity> purchaseEntityList = purchaseDao.getUnSyncedPayedEntity();

            // No need call sync bcs PurchaseEntity is empty
            if(purchaseEntityList.size() < 1){
                return;
            }

            PayedPurchaseDetailsList payedPurchaseDetailsList = new PayedPurchaseDetailsList();
            // Entity to Json
            for(PurchaseEntity purchaseEntity : purchaseEntityList){
                PayedPurchaseDetailsJson payedPurchaseDetailsJson = new PayedPurchaseDetailsJson(purchaseEntity);
                List<TransactionalDetailsEntity>  entityList = getTransactionDetails(purchaseEntity);
                payedPurchaseDetailsJson.getTransactions().addAll(entityList);
                payedPurchaseDetailsList.getPurchaseDetailsJsons().add(payedPurchaseDetailsJson);
            }

            userRequest(payedPurchaseDetailsList);
            // Sync Request
            Call<ResponseData> responseDataCall = mobilePayAPI.syncPayedData(payedPurchaseDetailsList);
            // Server Response
            Response<ResponseData> dataResponse = responseDataCall.execute();
            ResponseData responseData = dataResponse.body();
            // Success Response
            int statusCode = responseData.getStatusCode();
            if (statusCode == 200) {
                // Update ServerSync Time and IsSync Flag
                String response = responseData.getData();
                List<PurchaseJson> purchaseJsons = gson.fromJson(response, new TypeToken<List<PurchaseJson>>() {
                }.getType());
                purchaseDao.updateServerSyncTime(purchaseJsons);

            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in getUserDeliveryAddress", e);
        }
    }


    /**
     * Send UnSynced Declined Data to the server
     */
    public void sendUnSyncDeclineData() {
        try {
            List<PurchaseEntity> purchaseEntityList = purchaseDao.getUnSyncedDiscardEntity();
            DiscardJsonList discardJsonList = new DiscardJsonList();
            boolean isData = false;
            for (PurchaseEntity purchaseEntity : purchaseEntityList) {
                DiscardEntity discardEntity = purchaseDao.getDiscardEntity(purchaseEntity);
                DiscardJson discardJson = new DiscardJson(discardEntity, purchaseEntity);
                List<TransactionalDetailsEntity>  entityList = getTransactionDetails(purchaseEntity);
                discardJson.getTransactions().addAll(entityList);
                discardJsonList.getDiscardJsons().add(discardJson);
                isData = true;
            }
            if (isData) {
                userRequest(discardJsonList);
                // Sync Request
                Call<ResponseData> responseDataCall = mobilePayAPI.syncDiscardData(discardJsonList);
                // Server Response
                Response<ResponseData> dataResponse = responseDataCall.execute();
                ResponseData responseData = dataResponse.body();
                // Success Response
                int statusCode = responseData.getStatusCode();
                if (statusCode == 200) {
                    // Update ServerSync Time and IsSync Flag
                    String response = responseData.getData();
                    List<PurchaseJson> purchaseJsons = gson.fromJson(response, new TypeToken<List<PurchaseJson>>() {
                    }.getType());
                    purchaseDao.updateServerSyncTime(purchaseJsons);

                }
            }


        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in getUserDeliveryAddress", e);
        }
    }


    /**
     * Get TransactionalDetailsEntity
     * @param purchaseEntity
     * @return
     */
    private List<TransactionalDetailsEntity> getTransactionDetails(PurchaseEntity purchaseEntity){
        try {
            List<TransactionalDetailsEntity> transactionalDetailsEntities = purchaseDao.getTransactionalDetails(purchaseEntity);
            for(TransactionalDetailsEntity transactionalDetailsEntity : transactionalDetailsEntities){
                transactionalDetailsEntity.setPurchaseEntity(null);
                transactionalDetailsEntity.setTransactionId(0);
            }
            return transactionalDetailsEntities;
        }catch (Exception e){
            Log.e(LOG_TAG, "Error in processTransactionDetails", e);
        }
        return new ArrayList<>();

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

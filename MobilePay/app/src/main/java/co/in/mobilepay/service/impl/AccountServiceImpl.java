package co.in.mobilepay.service.impl;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;

import java.sql.SQLException;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.dao.impl.UserDaoImpl;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.enumeration.GsonAPI;
import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.json.response.UserJson;
import co.in.mobilepay.service.AccountService;
import co.in.mobilepay.service.PasswordHash;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.sync.MobilePaySyncAdapter;
import co.in.mobilepay.sync.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nithish on 22-01-2016.
 */
public class AccountServiceImpl extends BaseService implements AccountService {
    private UserDao userDao = null;

    private   Bundle settingsBundle;
    private  Account account ;

    public AccountServiceImpl(Context context)throws SQLException{
        super();
        userDao = new UserDaoImpl(context);
    }

    public void login(String password){
        try{
            UserEntity userEntity =  userDao.getUser();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("mobileNumber",userEntity.getMobileNumber());
            jsonObject.addProperty("password", password);
            Call<ResponseData> responseDataCall = mobilePayAPI.validateLoginDetails(jsonObject);
            AccountCallbackManager1 accountCallbackManager = new AccountCallbackManager1(3,null);
            responseDataCall.enqueue(accountCallbackManager);


        }catch (Exception e){
            Log.e("Error", "Error in login", e);
        }

    }

    @Override
    public void requestOtp(String mobileNumber,Context context) {
        init();
       account = MobilePaySyncAdapter.getSyncAccount(context);
        settingsBundle.putInt("currentScreen",MessageConstant.MOBILE);
        settingsBundle.putString("mobileNumber",mobileNumber);
        ContentResolver.requestSync(account, context.getString(R.string.auth_type), settingsBundle);
    }


    @Override
    public void validateOtp(String otpPassword, String mobileNumber, Context context) {
        init();
        account = MobilePaySyncAdapter.getSyncAccount(context);
        settingsBundle.putInt("currentScreen",MessageConstant.OTP);
        settingsBundle.putString("mobileNumber",mobileNumber);
        settingsBundle.putString("otpPassword",otpPassword);
        ContentResolver.requestSync(account, context.getString(R.string.auth_type), settingsBundle);
    }


    @Override
    public void validateOtp(String otpPassword, RegisterJson registerJson, Context context) {
        init();
        account = MobilePaySyncAdapter.getSyncAccount(context);
        settingsBundle.putInt("currentScreen",MessageConstant.OTP);
        settingsBundle.putParcelable("registerJson",registerJson);
        settingsBundle.putString("otpPassword",otpPassword);
        ContentResolver.requestSync(account, context.getString(R.string.auth_type), settingsBundle);
    }


    @Override
    public void createUser(RegisterJson registerJson, Context context) {
        init();
        account = MobilePaySyncAdapter.getSyncAccount(context);
        settingsBundle.putInt("currentScreen",MessageConstant.REGISTER);
        settingsBundle.putString("registration", GsonAPI.INSTANCE.getGson().toJson(registerJson));
        ContentResolver.requestSync(account, context.getString(R.string.auth_type), settingsBundle);
    }

    private void init(){
        if(settingsBundle == null){
            settingsBundle = new Bundle();
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_MANUAL, true);
            settingsBundle.putBoolean(
                    ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

        }

    }

    /*  public void resendOtp(String mobileNumber){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobileNumber",mobileNumber);
        Call<ResponseData> responseDataCall = mobilePayAPI.verifyMobileNo(jsonObject);
        AccountCallbackManager1 accountCallbackManager = new AccountCallbackManager1(5,null);
        responseDataCall.enqueue(accountCallbackManager);
    }


    public void verifyMobile(String mobileNumber){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobileNumber",mobileNumber);
        Call<ResponseData> responseDataCall = mobilePayAPI.verifyMobileNo(jsonObject);
        AccountCallbackManager1 accountCallbackManager = new AccountCallbackManager1(4);
        responseDataCall.enqueue(accountCallbackManager);
    }


    private void processMobileNoSuccessResponse(ResponseData responseData){
       MobilePayBus.getInstance().post(responseData);
    }
*/





    private void processLoginSuccessResponse(ResponseData responseData){
        int statusCode = responseData.getStatusCode();
        if(statusCode == MessageConstant.LOGIN_OK){
            try{
                UserJson userJson = gson.fromJson(responseData.getData(), UserJson.class);
                UserEntity userEntity =  userDao.getUser();
                userEntity.setAccessToken(userJson.getAccessToken());
                userEntity.setServerToken(userJson.getServerToken());
                userDao.updateUser(userEntity);
                ServiceAPI.INSTANCE.setAccessToken(userEntity.getAccessToken());
                ServiceAPI.INSTANCE.setServerToken(userEntity.getServerToken());
               MobilePayBus.getInstance().post(responseData);
                return;
            }catch (Exception e){
                Log.e("Error","Error in processLoginSuccessResponse",e);
            }
            responseData = new ResponseData();
            responseData.setStatusCode(MessageConstant.LOGIN_INTERNAL_ERROR);
            MobilePayBus.getInstance().post(responseData);
        }else{
            MobilePayBus.getInstance().post(responseData);
        }
    }

    /**
     * Check whether user is present or not.
     * @return
     */
    public boolean isUserPresent(){
        try{
            return userDao.isUserPresent();
        }catch (Exception e){
            Log.e("Error", "Error in isUserPresent", e);
        }
        return false;
    }


    @Override
    public UserEntity getUserDetails(){
        try{
            return  userDao.getUser();
        }catch (Exception e){
            Log.e("Error","Error in getUserDetails",e);
        }
        return null;
    }




    /**
     * Create new user with server communication
     * @param registerJson
     * @return
     */
   /* public void createUser(RegisterJson registerJson){
        deleteUser();
        String registerData = gson.toJson(registerJson);
        String regEncryption = null;
        try{
            regEncryption =  ServiceUtil.netEncryption(registerData);
        }catch (Exception e){
            e.printStackTrace();
            // Hope it never throw this error
        }
        AccountCallbackManager1 accountCallbackManager = new AccountCallbackManager1(1,registerJson);
        Call<ResponseData> dataCall =  mobilePayAPI.createUser(regEncryption);
        dataCall.enqueue(accountCallbackManager);
    }


    public void updateUser(RegisterJson registerJson){
        try{
            AccountCallbackManager1 accountCallbackManager = new AccountCallbackManager1(5,registerJson);
            Call<ResponseData> dataCall =  mobilePayAPI.updateUser(registerJson);
            dataCall.enqueue(accountCallbackManager);
        }catch (Exception e){
            Log.e("Error", "Error in updateUser", e);
            ResponseData  responseData = new ResponseData();
            responseData.setStatusCode(500);
            MobilePayBus.getInstance().post(responseData);
        }

    }

    private void processRegUpdateResponse( ResponseData responseData ,RegisterJson registerJson){
        try{
            int statusCode = responseData.getStatusCode();
            if(statusCode == 2){
                UserEntity userEntity =  userDao.getUser();
                userEntity.setName(registerJson.getName());
                userEntity.setEmail(registerJson.getEmail());
                userEntity.setMobileNumber(registerJson.getMobileNumber());
                userDao.updateUser(userEntity);
            }
            MobilePayBus.getInstance().post(responseData);
        }catch (Exception e){
            Log.e("Error", "Error in processRegResponse", e);
            responseData = new ResponseData();
            responseData.setStatusCode(500);
            MobilePayBus.getInstance().post(responseData);
        }
    }

    private void processRegResponse(ResponseData responseData, RegisterJson registerJson){
        try{
            int statusCode = responseData.getStatusCode();
            if(statusCode == MessageConstant.REG_OK){
                String passwordEncypt = passwordHash.createHash(registerJson.getPassword());
                UserEntity userEntity = new UserEntity(registerJson);
                userEntity.setPassword(passwordEncypt);
                userDao.createUser(userEntity);
            }
            MobilePayBus.getInstance().post(responseData);
            return;
        }catch (Exception e){
            Log.e("Error", "Error in processRegResponse", e);

        }
        responseData = new ResponseData();
        responseData.setStatusCode(500);
        responseData.setData(MessageConstant.REG_ERROR);
        MobilePayBus.getInstance().post(responseData);


    }*/



    /**
     * Check whether given otp is valid or not
     * @return
     */
  /*  public void validateOtp(String otpPassword,String mobileNumber) {
       try{
           JsonObject jsonObject = new JsonObject();
           jsonObject.addProperty("mobileNumber", mobileNumber);
           jsonObject.addProperty("otpPassword",otpPassword);
           Call<ResponseData> dataCall =   mobilePayAPI.validateOtp(jsonObject);
           AccountCallbackManager1 accountCallbackManager = new AccountCallbackManager1(2,null);
           dataCall.enqueue(accountCallbackManager);

       }catch (Exception e){
            Log.e("Error","Error in validateOtp",e);
       }
    }


    private void processOtpResponse(ResponseData responseData){
        MobilePayBus.getInstance().post(responseData);
    }*/


    private class AccountCallbackManager1 implements Callback<ResponseData>{
        int ops = 0;
        Object data = null;

        public AccountCallbackManager1(int ops,Object data){
            this.ops = ops;
            this.data = data;
        }

        @Override
        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
            switch (ops){
              /*  case 1:
                    processRegResponse(response.body(), (RegisterJson)data);
                    break;
                case 2:
                    processOtpResponse(response.body());
                    break;*/
                case 3:
                    processLoginSuccessResponse(response.body());
                    break;
              /*  case 4:
                    processMobileNoSuccessResponse(response.body());
                    break;
                case 5:
                    processRegUpdateResponse(response.body(),(RegisterJson)data);
                    break;*/
            }
        }

        @Override
        public void onFailure(Call<ResponseData> call, Throwable t) {
            t.printStackTrace();
            Log.e("Error","Error in Sync",t);
            switch (ops){
              /*  case 1:
                    ResponseData responseData = new ResponseData();
                    responseData.setStatusCode(MessageConstant.REG_ERROR_CODE);
                    processRegResponse(responseData,null);
                    break;
                case 2:
                    responseData = new ResponseData();
                    responseData.setStatusCode(MessageConstant.OTP_ERROR_CODE);
                    processOtpResponse(responseData);
                    break;*/
            }
        }
    }


  /*  @Override
    public void resetApp(String mobileNumber){
        try {
          UserEntity userEntity =  userDao.getUser();
            if(userEntity != null){
              if(!userEntity.getMobileNumber().equals(mobileNumber)){
                  userDao.removeData();
              }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(){
        try {
            userDao.removeUser();
        }catch (Exception e){
            e.printStackTrace();
        }

    }*/


}

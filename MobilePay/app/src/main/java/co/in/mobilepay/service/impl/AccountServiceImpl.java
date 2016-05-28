package co.in.mobilepay.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import java.sql.SQLException;

import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.dao.impl.UserDaoImpl;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.json.response.UserJson;
import co.in.mobilepay.service.AccountService;
import co.in.mobilepay.service.PasswordHash;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.sync.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nithish on 22-01-2016.
 */
public class AccountServiceImpl extends BaseService implements AccountService {
    private UserDao userDao = null;

    private PasswordHash passwordHash = null;

    public AccountServiceImpl(Context context)throws SQLException{
        super();
        userDao = new UserDaoImpl(context);
        passwordHash = new PasswordHashImpl();
    }

    public void login(String password,AccountServiceCallback accountServiceCallback){
        try{
            UserEntity userEntity =  userDao.getUser();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("mobileNumber",userEntity.getMobileNumber());
            jsonObject.addProperty("password", password);
            Call<ResponseData> responseDataCall = mobilePayAPI.validateLoginDetails(jsonObject);
            AccountCallbackManager accountCallbackManager = new AccountCallbackManager(3,null,accountServiceCallback);
            responseDataCall.enqueue(accountCallbackManager);


        }catch (Exception e){
            Log.e("Error", "Error in login", e);
        }

    }

    public void resendOtp(String mobileNumber){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobileNumber",mobileNumber);
        Call<ResponseData> responseDataCall = mobilePayAPI.verifyMobileNo(jsonObject);
        AccountCallbackManager accountCallbackManager = new AccountCallbackManager(5,null,null);
        responseDataCall.enqueue(accountCallbackManager);
    }

    public void verifyMobile(String mobileNumber,AccountServiceCallback accountServiceCallback){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("mobileNumber",mobileNumber);
        Call<ResponseData> responseDataCall = mobilePayAPI.verifyMobileNo(jsonObject);
        AccountCallbackManager accountCallbackManager = new AccountCallbackManager(4,null,accountServiceCallback);
        responseDataCall.enqueue(accountCallbackManager);
    }

    private void processMobileNoSuccessResponse(ResponseData responseData,String mobileNo,AccountServiceCallback accountServiceCallback){
        int statusCode = responseData.getStatusCode();
        accountServiceCallback.accountServiceCallback(statusCode, mobileNo);

    }

    private void processLoginSuccessResponse(ResponseData responseData,AccountServiceCallback accountServiceCallback){
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
                accountServiceCallback.accountServiceCallback(MessageConstant.LOGIN_OK,null);
                return;
            }catch (Exception e){
                Log.e("Error","Error in processLoginSuccessResponse",e);
            }
            accountServiceCallback.accountServiceCallback(MessageConstant.LOGIN_INTERNAL_ERROR,null);
        }else{
            accountServiceCallback.accountServiceCallback(statusCode,responseData.getData());
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
    public void createUser(RegisterJson registerJson,AccountServiceCallback accountServiceCallback){
        deleteUser();
        String registerData = gson.toJson(registerJson);
        String regEncryption = null;
        try{
            regEncryption =  ServiceUtil.netEncryption(registerData);
        }catch (Exception e){
            e.printStackTrace();
            // Hope it never throw this error
        }
        AccountCallbackManager accountCallbackManager = new AccountCallbackManager(1,registerJson,accountServiceCallback);
        Call<ResponseData> dataCall =  mobilePayAPI.createUser(regEncryption);
        dataCall.enqueue(accountCallbackManager);
    }


    public void updateUser(RegisterJson registerJson){
        try{
            AccountCallbackManager accountCallbackManager = new AccountCallbackManager(5,registerJson,null);
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
                userEntity.setMobileNumber(registerJson.getMobileNumber());
                userDao.updateUser(userEntity);
            }
            MobilePayBus.getInstance().post(responseData);
        }catch (Exception e){
            Log.e("Error", "Error in processRegSuccessResponse", e);
            responseData = new ResponseData();
            responseData.setStatusCode(500);
            MobilePayBus.getInstance().post(responseData);
        }
    }

    private void processRegSuccessResponse(Response<ResponseData> response,RegisterJson registerJson,AccountServiceCallback accountServiceCallback){
        ResponseData responseData = response.body();
        try{
            int statusCode = response.code();
            if(statusCode == 200){
                String passwordEncypt = passwordHash.createHash(registerJson.getPassword());
                UserEntity userEntity = new UserEntity(registerJson);
                userEntity.setPassword(passwordEncypt);
                userDao.createUser(userEntity);
                accountServiceCallback.accountServiceCallback(responseData.getStatusCode(), null);
                return;
            }else{
                accountServiceCallback.accountServiceCallback(responseData.getStatusCode(), responseData.getData());
                return;
            }
        }catch (Exception e){
            Log.e("Error", "Error in processRegSuccessResponse", e);
        }

        accountServiceCallback.accountServiceCallback(MessageConstant.REG_ERROR_CODE, null);

    }

    private void processRegFailureResponse(AccountServiceCallback accountServiceCallback){
        accountServiceCallback.accountServiceCallback(MessageConstant.REG_ERROR_CODE,null);

    }

    /**
     * Check whether given otp is valid or not
     * @param otpPassword
     * @return
     */
    @Override
    public void validateOtp(String otpPassword,String mobileNumber) {
       try{
           JsonObject jsonObject = new JsonObject();
           jsonObject.addProperty("mobileNumber", mobileNumber);
           jsonObject.addProperty("otpPassword",otpPassword);
           Call<ResponseData> dataCall =   mobilePayAPI.validateOtp(jsonObject);
           AccountCallbackManager accountCallbackManager = new AccountCallbackManager(2,null,null);
           dataCall.enqueue(accountCallbackManager);

       }catch (Exception e){
            Log.e("Error","Error in validateOtp",e);
       }
    }


    private void processOtpResponse(ResponseData responseData){
        MobilePayBus.getInstance().post(responseData);
    }


    private class AccountCallbackManager implements Callback<ResponseData>{
        int ops = 0;
        Object data = null;
        AccountServiceCallback accountServiceCallback = null;

        public AccountCallbackManager(int ops,Object data,AccountServiceCallback accountServiceCallback ){
            this.ops = ops;
            this.data = data;
            this.accountServiceCallback = accountServiceCallback;
        }

        @Override
        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
            switch (ops){
                case 1:
                    processRegSuccessResponse(response, (RegisterJson)data,accountServiceCallback);
                    break;
                case 2:
                    processOtpResponse(response.body());
                    break;
                case 3:
                    processLoginSuccessResponse(response.body(),accountServiceCallback);
                    break;
                case 4:
                    processMobileNoSuccessResponse(response.body(),(String)data,accountServiceCallback);
                    break;
                case 5:
                    processRegUpdateResponse(response.body(),(RegisterJson)data);
                    break;
            }
        }

        @Override
        public void onFailure(Call<ResponseData> call, Throwable t) {
            t.printStackTrace();
            Log.e("Error","Error in Sync",t);
            switch (ops){
                case 1:
                    processRegFailureResponse(accountServiceCallback);
                    break;
                case 2:
                    ResponseData responseData = new ResponseData();
                    responseData.setStatusCode(MessageConstant.OTP_ERROR_CODE);
                    processOtpResponse(responseData);
                    break;
            }
        }


    }

    @Override
    public void deleteUser(){
        try {
            userDao.removeUser();;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public interface AccountServiceCallback{
         void accountServiceCallback(int statusCode,Object data);
    }
}

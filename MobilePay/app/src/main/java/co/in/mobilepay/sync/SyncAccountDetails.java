package co.in.mobilepay.sync;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.dao.impl.UserDaoImpl;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.enumeration.GsonAPI;
import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.impl.MessageConstant;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Nithishkumar on 6/8/2016.
 */
public class SyncAccountDetails {

    public final String LOG_TAG = SyncAccountDetails.class.getSimpleName();
    private MobilePayAPI mobilePayAPI;
    private UserDao userDao;
    private Context context;
    private Gson gson;

    public SyncAccountDetails(Context context){
        this.context = context;
        init();
    }

    private void init(){
        try{
            userDao = new UserDaoImpl(context);
            gson = GsonAPI.INSTANCE.getGson();
        }catch (Exception e){
            Log.e(LOG_TAG,"Error in init",e);
        }
        mobilePayAPI = ServiceAPI.INSTANCE.getMobilePayAPI();
    }


    /**
     * Request OTP
     * @param mobileNumber
     * @return
     */
    public ResponseData requestOtp(String mobileNumber){
        try{
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("mobileNumber",mobileNumber);
            Call<ResponseData> dataCall = mobilePayAPI.verifyMobileNo(jsonObject);
            Response<ResponseData> response = dataCall.execute();
            if(response != null && response.isSuccess()){
                return response.body();
            }else{
                logErrorResponse(response);
            }
        }catch (Exception e){
            Log.e(LOG_TAG,"Error in requestOtp,mobileNumber["+mobileNumber+"]",e);
        }
        return getErrorResponse();
    }


    private void logErrorResponse(Response<ResponseData> response){
        if(response == null){
            Log.e(LOG_TAG,"Response is null");
        }else{
            Log.e(LOG_TAG,"Response status["+response.isSuccess()+"], Response message["+response.message()+"]");
        }
    }


    /**
     * Get User Profile from the server
     * @return
     */
    public ResponseData getUserProfile(){
        try{
            Call<ResponseData> dataCall =  mobilePayAPI.getUserProfile();
            Response<ResponseData> response = dataCall.execute();
            if(response != null && response.isSuccess()){
                ResponseData responseData = response.body();
                int statusCode = response.body().getStatusCode();
                if(statusCode == MessageConstant.PROFILE_OK){
                    UserEntity userEntity =   userDao.getUser();
                    String profileData = responseData.getData();
                    RegisterJson registerJson =  gson.fromJson(profileData,RegisterJson.class);
                    userEntity.toUser(registerJson);
                    userDao.updateUser(userEntity);
                }
                return responseData;
            }else{
                logErrorResponse(response);
            }

        }catch (Exception e){
            Log.e(LOG_TAG,"Error in userRegistration",e);
        }
        return getErrorResponse();
    }



    /**
     * Get User Profile from the server
     * @return
     */
    public ResponseData getUserProfile(String mobileNumber){
        try{
            Call<ResponseData> dataCall =  mobilePayAPI.getUserProfile(mobileNumber);
            Response<ResponseData> response = dataCall.execute();
            if(response != null && response.isSuccess()){
                ResponseData responseData = response.body();
                return responseData;
            }else{
                logErrorResponse(response);
            }

        }catch (Exception e){
            Log.e(LOG_TAG,"Error in userRegistration",e);
        }
        return getErrorResponse();
    }


    /**
     * Create New User
     * @param registerJson
     * @return
     */
    public ResponseData userRegistration(RegisterJson registerJson){
        try{
           // String registerData = gson.toJson(registerJson);
            Call<ResponseData> dataCall =  mobilePayAPI.createUser(registerJson);
            Response<ResponseData> response = dataCall.execute();
            if(response != null && response.isSuccess()){
                int statusCode = response.body().getStatusCode();
                if(statusCode == MessageConstant.REG_OK){
                    deleteUser();
                    UserEntity userEntity = new UserEntity(registerJson);
                    userDao.createUser(userEntity);
                }
                return response.body();
            }else{
                logErrorResponse(response);
            }

        }catch (Exception e){
            Log.e(LOG_TAG,"Error in userRegistration,registerJson["+registerJson+"]",e);
        }
        return getErrorResponse();
    }


    public ResponseData validateOtp(String otpPassword,RegisterJson registerJson){
        try{
            ResponseData responseData =  validateOtp(otpPassword,registerJson.getMobileNumber());
            if(responseData.getStatusCode() == MessageConstant.OTP_OK){
                return userRegistration(registerJson);
            }
            return responseData;
        }catch (Exception e){
            Log.e(LOG_TAG,"Error in validateOtp,otpPassword["+otpPassword+"],registerJson["+registerJson+"]",e);
        }
        return getErrorResponse();
    }



    /**
     * Check whether given otp is valid or not
     * @param otpPassword
     * @return
     */
    public ResponseData validateOtp(String otpPassword,String mobileNumber) {
        try{
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("mobileNumber", mobileNumber);
            jsonObject.addProperty("otpPassword",otpPassword);
            Call<ResponseData> dataCall =   mobilePayAPI.validateOtp(jsonObject);
            Response<ResponseData> response = dataCall.execute();
            if(response != null && response.isSuccess()){
                resetApp(mobileNumber);
                return response.body();
            }else{
                logErrorResponse(response);
            }

        }catch (Exception e){
            Log.e(LOG_TAG,"Error in validate,OtpmobileNumber["+mobileNumber+"],otpPassword["+otpPassword+"]",e);
        }
        return getErrorResponse();
    }


    /**
     * Clears entire database
     * @param mobileNumber
     */
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


    private ResponseData getErrorResponse(){
        ResponseData responseData =new ResponseData();
        responseData.setStatusCode(500);
        return responseData;
    }


    /**
     * If User is available, then delete
     */
    public void deleteUser(){
        try {
            UserEntity userEntity =  userDao.getUser();
            if(userEntity != null){
                userDao.removeUser();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

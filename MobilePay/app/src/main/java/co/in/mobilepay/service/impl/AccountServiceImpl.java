package co.in.mobilepay.service.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.SQLException;

import co.in.mobilepay.Sync.MobilePayAPI;
import co.in.mobilepay.Sync.ServiceAPI;
import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.dao.impl.UserDaoImpl;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.AccountService;
import co.in.mobilepay.service.PasswordHash;
import co.in.mobilepay.service.ServiceUtil;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Nithish on 22-01-2016.
 */
public class AccountServiceImpl implements AccountService {
    private UserDao userDao = null;
    private MobilePayAPI mobilePayAPI = null;
    private Gson gson = null;
    private PasswordHash passwordHash = null;

    public AccountServiceImpl(Context context)throws SQLException{
        userDao = new UserDaoImpl(context);
        mobilePayAPI = ServiceAPI.INSTANCE.getMobilePayAPI();
        gson = new Gson();
    }

    public Response<ResponseData> login(String password){
        try{
            UserEntity userEntity =  userDao.getUser();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("mobileNumber",userEntity.getMobileNumber());
            jsonObject.addProperty("password", userEntity.getPassword());
            Call<ResponseData> responseDataCall = mobilePayAPI.validateLoginDetails(jsonObject.toString());
            Response<ResponseData> dataResponse = responseDataCall.execute();
            int responseCode = dataResponse.code();
            if(responseCode == 200){
               String token = dataResponse.raw().message();
                userEntity.setAccessToken(token);
                userDao.updateUser(userEntity);
            }
           return dataResponse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Check whether user is present or not.
     * @return
     */
    public boolean isUserPresent(){
        try{
            return userDao.isUserPresent();
        }catch (Exception e){
            Log.e("Error","Error in isUserPresent",e);
        }
        return false;
    }

    /**
     * Create new user with server communication
     * @param registerJson
     * @return
     */
    public Response<ResponseData> createUser(RegisterJson registerJson){
        String registerData = gson.toJson(registerJson);
        String regEncryption = null;
        try{
            regEncryption =  ServiceUtil.netEncryption(registerData);
        }catch (Exception e){
            // Hope it never throw this error
        }
        try{
            Call<ResponseData> dataCall =  mobilePayAPI.createUser(regEncryption);
            Response<ResponseData> dataResponse = dataCall.execute();
            int statusCode = dataResponse.code();
            if(statusCode == 200){
                String passwordEncypt = passwordHash.createHash(registerJson.getPassword());
                UserEntity userEntity = new UserEntity(registerJson);
                userEntity.setPassword(passwordEncypt);
                userDao.createUser(userEntity);
            }

            return dataResponse;
        }catch (Exception e){
            e.printStackTrace();
            // Need to handle Exception
        }
        return null;

    }

    /**
     * Check whether given otp is valid or not
     * @param otpPassword
     * @return
     */
    @Override
    public Response<ResponseData> validateOtp(String otpPassword) {
       try{
           UserEntity userEntity =  userDao.getUser();
           JsonObject jsonObject = new JsonObject();
           jsonObject.addProperty("mobileNumber", userEntity.getMobileNumber());
           jsonObject.addProperty("otpPassword",otpPassword);
           Call<ResponseData> dataCall =   mobilePayAPI.validateOtp(jsonObject.toString());
           Response<ResponseData> dataResponse = dataCall.execute();
           int statusCode = dataResponse.code();
           if(statusCode == 200){
               userDao.updateUser();
           }
           return dataResponse;
       }catch (Exception e){
            Log.e("Error","Error in validateOtp",e);
       }
        return null;
    }
}

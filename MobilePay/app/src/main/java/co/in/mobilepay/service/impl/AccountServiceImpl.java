package co.in.mobilepay.service.impl;

import android.content.Context;

import com.google.gson.Gson;

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

    public boolean login(String password){
        try{
            UserEntity userEntity =  userDao.getUser();
            String hashPassword = userEntity.getPassword();
            boolean isValid =  passwordHash.validatePassword(password,hashPassword);
            return isValid;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }

    public void createUser(String mobileNumber,String name,String password,String imei){
        RegisterJson registerJson = new RegisterJson(name, password, mobileNumber,imei);
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
            ResponseData responseData = dataResponse.body();
            // Need to handle success or failure
            String passwordEncypt = passwordHash.createHash(password);
            UserEntity userEntity = new UserEntity(registerJson);
            userEntity.setPassword(passwordEncypt);
            userDao.createUser(userEntity);
            //
        }catch (Exception e){
            e.printStackTrace();
            // Need to handle Exception
        }


    }
}

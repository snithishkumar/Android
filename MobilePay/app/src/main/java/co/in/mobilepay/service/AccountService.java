package co.in.mobilepay.service;

import android.content.Context;

import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.json.request.RegisterJson;

/**
 * Created by Nithish on 22-01-2016.
 */
public interface AccountService {


   void createUser(RegisterJson registerJson,Context context);

    boolean isUserPresent();

    void getUserProfile(Context context);

    UserEntity getUserDetails();

    void requestOtp(String mobileNumber,Context context);

   void validateOtp(String otpPassword,String mobileNumber,Context context);

    void validateOtp(String otpPassword, RegisterJson registerJson, Context context);

    void getUserProfile(String mobileNumber,Context context);

    UserEntity getUserDetails(String mobileNumber);

 /*   void updateUser(RegisterJson registerJson);

    void deleteUser();*/
    void login(String password);

  /*  void verifyMobile(String mobileNumber);

    void resendOtp(String mobileNumber);

    void resetApp(String mobileNumber);*/
}

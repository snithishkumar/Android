package co.in.mobilepay.service;

import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.impl.AccountServiceImpl;
import retrofit2.Response;

/**
 * Created by Nithish on 22-01-2016.
 */
public interface AccountService {

    void createUser(RegisterJson registerJson,AccountServiceImpl.AccountServiceCallback accountServiceCallback);

    boolean isUserPresent();

   void validateOtp(String otpPassword,String mobileNumber,AccountServiceImpl.AccountServiceCallback accountServiceCallback);


    void login(String password,AccountServiceImpl.AccountServiceCallback accountServiceCallback);

    void verifyMobile(String mobileNumber,AccountServiceImpl.AccountServiceCallback accountServiceCallback);
}

package co.in.mobilepay.service;

import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.ResponseData;
import retrofit2.Response;

/**
 * Created by Nithish on 22-01-2016.
 */
public interface AccountService {

    Response<ResponseData> createUser(RegisterJson registerJson);

    boolean isUserPresent();

    Response<ResponseData> validateOtp(String otpPassword);
}

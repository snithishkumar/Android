package co.in.mobilepay.Sync;

import co.in.mobilepay.json.response.ResponseData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Nithish on 23-01-2016.
 */
public interface  MobilePayAPI {

    @POST("/mobile/register.html")
    Call<ResponseData> createUser(@Body String data);

    @POST("/mobile/otp/validate.html")
    Call<ResponseData> validateOtp(@Body String data);
}

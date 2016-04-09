package co.in.mobilepay.sync;

import com.google.gson.JsonObject;

import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.AddressBookJson;
import co.in.mobilepay.json.response.CardJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.json.response.TokenJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Nithish on 23-01-2016.
 */
public interface  MobilePayAPI {

    @POST("mobile/register.html")
    Call<ResponseData> createUser(@Body String data);

    @POST("mobile/updateProfile.html")
    Call<ResponseData> updateUser(@Body RegisterJson data);

    @POST("mobile/otp/validate.html")
    Call<ResponseData> validateOtp(@Body JsonObject jsonObject);

    @POST("mobile/verifyMobileNo.html")
    Call<ResponseData> verifyMobileNo(@Body JsonObject jsonObject);

    @POST("mobile/loginByMobileNumber.html")
    Call<ResponseData> validateLoginDetails(@Body JsonObject jsonObject);

    @POST("mobile/getPurchaseList.html")
    Call<ResponseData> syncPurchaseData(@Body JsonObject requestData);

    @POST("mobile/getLuggageList.html")
    Call<ResponseData> syncOrderStatus(@Body JsonObject requestData);

    @POST("mobile/getPurchaseHistoryList.html")
    Call<ResponseData> syncPurchaseHistoryData(@Body JsonObject requestData);

    @POST("mobile/addCards.html")
    Call<ResponseData> createCard(@Body CardJson cardJson);

    @POST("mobile/removeCards.html")
    Call<ResponseData> removeCard(@Body CardJson cardJson);

    @POST("mobile/getCardList.html")
    Call<ResponseData> getCardList(@Body TokenJson tokenJson);

    @POST("mobile/syncUserDeliveryAddress.html")
    Call<ResponseData> syncUserDeliveryAddress(@Body AddressBookJson requestData);
}

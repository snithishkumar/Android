package co.in.mobilepay.sync;

import com.google.gson.JsonObject;

import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.AddressBookJson;
import co.in.mobilepay.json.response.CardJson;
import co.in.mobilepay.json.response.CloudMessageJson;
import co.in.mobilepay.json.response.DiscardJson;
import co.in.mobilepay.json.response.DiscardJsonList;
import co.in.mobilepay.json.response.GetPurchaseDetailsList;
import co.in.mobilepay.json.response.PayedPurchaseDetailsList;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.json.response.TokenJson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

/**
 * Created by Nithish on 23-01-2016.
 */
public interface  MobilePayAPI {

    /** Create User **/
    @POST("mobile/register.html")
    Call<ResponseData> createUser(@Body String data);

    /** Update Profile Changes **/
    @POST("mobile/updateProfile.html")
    Call<ResponseData> updateUser(@Body RegisterJson data);

    /** Verify OTP Password **/
    @POST("mobile/otp/validate.html")
    Call<ResponseData> validateOtp(@Body JsonObject jsonObject);

    /** Send request to Get OTP Password **/
    @POST("mobile/verifyMobileNo.html")
    Call<ResponseData> verifyMobileNo(@Body JsonObject jsonObject);

    /** Login **/
    @POST("mobile/loginByMobileNumber.html")
    Call<ResponseData> validateLoginDetails(@Body JsonObject jsonObject);

    /** Get Current Purchase List of UUIDs**/
    @POST("mobile/getPurchaseList.html")
    Call<ResponseData> syncPurchaseListFromServer(@Body TokenJson requestData);

    /** Get Purchase Details Data for given UUIDs **/
    @POST("mobile/getPurchaseDetails.html")
    Call<ResponseData> syncPurchaseDetailsData(@Body GetPurchaseDetailsList requestData);

    /** Get Order Status. It contains both Order Updates and Purchase Details **/
    @POST("mobile/getLuggageList.html")
    Call<ResponseData> syncOrderStatus(@Body JsonObject requestData);

    /** Get Last 25 Purchase History List**/
    @POST("mobile/getPurchaseHistoryList.html")
    Call<ResponseData> syncPurchaseHistoryList(@Body TokenJson requestData);

    /** Save Card Data **/
    @POST("mobile/addCards.html")
    Call<ResponseData> createCard(@Body CardJson cardJson);

    /** Remove already added cards **/
    @POST("mobile/removeCards.html")
    Call<ResponseData> removeCard(@Body CardJson cardJson);

    /** Get Card List **/
    @POST("mobile/getCardList.html")
    Call<ResponseData> getCardList(@Body TokenJson tokenJson);

    /** Send User Home Address **/
    @POST("mobile/syncUserDeliveryAddress.html")
    Call<ResponseData> syncUserDeliveryAddress(@Body AddressBookJson requestData);

    /** Send Cancel Data to the server **/
    @POST("mobile/syncDiscardData.html")
    Call<ResponseData> syncDiscardData(@Body DiscardJsonList requestData);

    /** Send Payed Data to the server **/
    @POST("mobile/syncPayedData.html")
    Call<ResponseData> syncPayedData(@Body PayedPurchaseDetailsList requestData);

    /** Send GCM Id to the server **/
    @POST("mobile/addCloudId.html")
    Call<ResponseData> addCloudId(@Body CloudMessageJson cloudMessageJson);


}

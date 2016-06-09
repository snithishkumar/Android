package co.in.mobilepay.sync;

import com.google.gson.JsonObject;

import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.AddressBookJson;
import co.in.mobilepay.json.response.CloudMessageJson;
import co.in.mobilepay.json.response.DiscardJsonList;
import co.in.mobilepay.json.response.GetPurchaseDetailsList;
import co.in.mobilepay.json.response.PayedPurchaseDetailsList;
import co.in.mobilepay.json.response.ResponseData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Nithish on 23-01-2016.
 */
public interface  MobilePayAPI {

    /** Create User **/
    @POST("mobile/register.html")
    Call<ResponseData> createUser(@Body RegisterJson registerJson);

    /** Update Profile Changes **/
    @POST("mobilePayUser/mobile/updateProfile.html")
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
    @POST("mobilePayUser/mobile/getPurchaseList.html")
    Call<ResponseData> syncPurchaseListFromServer();

    /** Get Purchase Details Data for given UUIDs **/
    @POST("mobilePayUser/mobile/getPurchaseDetails.html")
    Call<ResponseData> syncPurchaseDetailsData(@Body GetPurchaseDetailsList requestData);

    /** Get Order Status. It contains both Order Updates and Purchase Details **/
    @POST("mobilePayUser/mobile/getOrderStatusList.html")
    Call<ResponseData> syncOrderStatus(@Body JsonObject requestData);

    /** Get Last 25 Purchase History List**/
    @POST("mobilePayUser/mobile/getPurchaseHistoryList.html")
    Call<ResponseData> syncPurchaseHistoryList();

    /** Send User Home Address **/
    @POST("mobile/syncUserDeliveryAddress.html")
    Call<ResponseData> syncUserDeliveryAddress(@Body AddressBookJson requestData);

    /** Send Cancel Data to the server **/
    @POST("mobilePayUser/mobile/syncDiscardData.html")
    Call<ResponseData> syncDiscardData(@Body DiscardJsonList requestData);

    /** Send Payed Data to the server **/
    @POST("mobilePayUser/mobile/syncPayedData.html")
    Call<ResponseData> syncPayedData(@Body PayedPurchaseDetailsList requestData);

    /** Send GCM Id to the server **/
    @POST("mobilePayUser/mobile/addCloudId.html")
    Call<ResponseData> addCloudId(@Body CloudMessageJson cloudMessageJson);


}

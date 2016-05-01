package co.in.mobilepay.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import co.in.mobilepay.R;
import co.in.mobilepay.dao.UserDao;
import co.in.mobilepay.dao.impl.UserDaoImpl;
import co.in.mobilepay.entity.UserEntity;
import co.in.mobilepay.enumeration.DeviceType;
import co.in.mobilepay.json.response.CloudMessageJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.sync.MobilePayAPI;
import co.in.mobilepay.sync.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nithishkumar on 4/20/2016.
 */
public class GcmRegistrationIntentService extends IntentService {

    private static final String TAG = "GcmRegistrationIntentService";

    private static final String TAG_1 = "GcmRegistration";

    public GcmRegistrationIntentService() {
        super(TAG);
    }


    /**
     * Get Cloud id and send to server
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        try{
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken( getString(R.string.google_cloud_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            UserDao userDao = new UserDaoImpl(getApplicationContext());
            UserEntity userEntity = userDao.getUser();
            if(userEntity == null){
                return;
            }
            MobilePayAPI mobilePayAPI = ServiceAPI.INSTANCE.getMobilePayAPI();
            CloudMessageJson cloudMessageJson = new CloudMessageJson();
            cloudMessageJson.setAccessToken(userEntity.getAccessToken());
            cloudMessageJson.setServerToken(userEntity.getServerToken());
            String imeiNumber = ServiceUtil.getIMEINumber(getApplicationContext());
            cloudMessageJson.setImeiNumber(imeiNumber);
            cloudMessageJson.setDeviceType(DeviceType.Android);
            cloudMessageJson.setCloudId(token);
            Call<ResponseData>responseDataCall = mobilePayAPI.addCloudId(cloudMessageJson);
            responseDataCall.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Log.e(TAG_1,"Error in addGcmService call",t);
                }
            });
        }catch (Exception e){
            Log.e(TAG_1, "Error in onHandleIntent", e);
        }

    }
}

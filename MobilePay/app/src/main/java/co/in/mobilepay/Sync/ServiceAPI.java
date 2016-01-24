package co.in.mobilepay.Sync;

import retrofit2.Retrofit;

/**
 * Initialize REST call
 * Created by Nithish on 23-01-2016.
 */
public enum ServiceAPI {
    INSTANCE;
    private MobilePayAPI mobilePayAPI = null;

    ServiceAPI(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("").build();
        mobilePayAPI = retrofit.create(MobilePayAPI.class);
    }

    public MobilePayAPI getMobilePayAPI(){
        return mobilePayAPI;
    }
}

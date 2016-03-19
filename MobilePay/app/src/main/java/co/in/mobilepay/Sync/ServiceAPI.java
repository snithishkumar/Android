package co.in.mobilepay.sync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Initialize REST call
 * Created by Nithish on 23-01-2016.
 */
public enum ServiceAPI {
    INSTANCE;
    private MobilePayAPI mobilePayAPI = null;

    ServiceAPI(){
        Executor executor = Executors.newCachedThreadPool();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.5:8081/mobilepay/").addConverterFactory(GsonConverterFactory.create()).callbackExecutor(executor).build();
        mobilePayAPI = retrofit.create(MobilePayAPI.class);
    }

    public MobilePayAPI getMobilePayAPI(){
        return mobilePayAPI;
    }
}

package co.in.mobilepay.sync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import co.in.mobilepay.util.MobilePayUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Initialize REST call
 * Created by Nithish on 23-01-2016.
 */
public enum ServiceAPI {
    INSTANCE;
    private MobilePayAPI mobilePayAPI = null;
    private final String  url = "http://124.125.202.60:8082/mobilepay/";

    ServiceAPI(){
        Executor executor = Executors.newCachedThreadPool();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).callbackExecutor(executor).build();
        mobilePayAPI = retrofit.create(MobilePayAPI.class);
    }

    public MobilePayAPI getMobilePayAPI(){
        return mobilePayAPI;
    }

    public String getUrl(){
        return url;
    }
}

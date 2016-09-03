package co.in.mobilepay.sync;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Initialize REST call
 * Created by Nithish on 23-01-2016.
 */
public enum ServiceAPI {
    INSTANCE;
    private MobilePayAPI mobilePayAPI = null;
    private final String  url = "http://52.41.226.201:8080/mobilepay/";
    // test
   //private final String  url = "http://192.168.0.7:8082/mobilepay/";
    private String serverToken= "";
    private String accessToken = "";

    ServiceAPI(){
        Executor executor = Executors.newCachedThreadPool();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(5, TimeUnit.MINUTES);
        httpClient.connectTimeout(5, TimeUnit.MINUTES);
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request().newBuilder().addHeader("mobilePay","user").addHeader("accessToken",accessToken).addHeader("serverToken",serverToken).build();
                return chain.proceed(original);
            }
        });

        OkHttpClient okHttpClient = httpClient.build();


        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).callbackExecutor(executor).build();
        mobilePayAPI = retrofit.create(MobilePayAPI.class);
    }

    public MobilePayAPI getMobilePayAPI(){
        return mobilePayAPI;
    }

    public String getUrl(){
        return url;
    }

    public void setServerToken(String serverToken) {
        this.serverToken = serverToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

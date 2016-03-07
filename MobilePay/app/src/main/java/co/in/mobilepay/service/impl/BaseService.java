package co.in.mobilepay.service.impl;

import com.google.gson.Gson;

import co.in.mobilepay.sync.MobilePayAPI;
import co.in.mobilepay.sync.ServiceAPI;

/**
 * Created by Nithish on 05-03-2016.
 */
public class BaseService  {
    protected MobilePayAPI mobilePayAPI = null;
    protected Gson gson = null;
    public BaseService(){
        mobilePayAPI = ServiceAPI.INSTANCE.getMobilePayAPI();
        gson = new Gson();
    }
}

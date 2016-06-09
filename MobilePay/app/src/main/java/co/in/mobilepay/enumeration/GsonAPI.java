package co.in.mobilepay.enumeration;

import com.google.gson.Gson;

/**
 * Created by Nithishkumar on 6/8/2016.
 */
public enum GsonAPI {
    INSTANCE;
    Gson gson = null;
    GsonAPI(){
        gson = new Gson();
    }

    public Gson getGson(){
        return gson;
    }
}

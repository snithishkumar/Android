package co.in.mobilepay.bus;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by Nithish on 09-03-2016.
 */
public class MobilePayBus extends Application {
    private static Bus mEventBus;

    public static Bus getInstance() {
        if(mEventBus == null){
            mEventBus  = new MainThreadBus();
        }
        return mEventBus;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mEventBus = new Bus();
    }

    /**
     * Be able to post from any thread to main thread
     */


    public static class MainThreadBus extends Bus {
        private final Handler handler = new Handler(Looper.getMainLooper());

        @Override public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                super.post(event);
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainThreadBus.super.post(event);
                    }
                });
            }
        }
    }
}

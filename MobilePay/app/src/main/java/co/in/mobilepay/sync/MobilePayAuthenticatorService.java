package co.in.mobilepay.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Nithish on 05-03-2016.
 *
 * The service which allows the sync adapter framework to access the authenticator.
 */
public class MobilePayAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private MobilePayAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new MobilePayAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}

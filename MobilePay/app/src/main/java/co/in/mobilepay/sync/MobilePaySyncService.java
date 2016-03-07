package co.in.mobilepay.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Nithish on 05-03-2016.
 */
public class MobilePaySyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static MobilePaySyncAdapter sMobilePaySyncAdapter = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MobilePaySyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (sMobilePaySyncAdapter == null) {
                sMobilePaySyncAdapter = new MobilePaySyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sMobilePaySyncAdapter.getSyncAdapterBinder();
    }
}

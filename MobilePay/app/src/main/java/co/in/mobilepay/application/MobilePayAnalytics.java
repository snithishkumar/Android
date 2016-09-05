package co.in.mobilepay.application;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

import java.util.Map;

/**
 * Created by Nithishkumar on 7/4/2016.
 */
public class MobilePayAnalytics extends Application {

    public static final String TAG = MobilePayAnalytics.class
            .getSimpleName();

    private static MobilePayAnalytics mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        MobilePayAnalyticsConf.initialize(this);
        MobilePayAnalyticsConf.getInstance().get(MobilePayAnalyticsConf.Target.APP);
    }

    public static synchronized MobilePayAnalytics getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        MobilePayAnalyticsConf analyticsTrackers = MobilePayAnalyticsConf.getInstance();
        return analyticsTrackers.get(MobilePayAnalyticsConf.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e,String data) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();
            Map<String,String> hitBuilders = new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build();
            hitBuilders.put("message",data);
            t.send(hitBuilders);
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }

}

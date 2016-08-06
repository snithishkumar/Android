package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.in.mobilepay.R;
import co.in.mobilepay.application.MobilePayAnalytics;
import co.in.mobilepay.view.activities.NaviDrawerActivity;

/**
 * Created by Nithishkumar on 6/17/2016.
 */
public class AboutAsFragment  extends Fragment {

    private NaviDrawerActivity naviDrawerActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        naviDrawerActivity.getSupportActionBar().setTitle("About Us");
        initBackButton();
        return view;
    }


    private void initBackButton(){
        final Drawable upArrow = ContextCompat.getDrawable(naviDrawerActivity,R.drawable.ic_arrow_back_white_24dp);;
        //upArrow.setColorFilter( ContextCompat.getColor(naviDrawerActivity,R.color.white), PorterDuff.Mode.SRC_ATOP);
        naviDrawerActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        naviDrawerActivity.getSupportActionBar().setHomeButtonEnabled(true);
        naviDrawerActivity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    public void onResume() {
        MobilePayAnalytics.getInstance().trackScreenView("AboutUS-F Screen");
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.naviDrawerActivity = (NaviDrawerActivity)context;
    }

}

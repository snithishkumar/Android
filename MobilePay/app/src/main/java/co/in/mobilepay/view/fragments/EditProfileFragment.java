package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.in.mobilepay.R;
import co.in.mobilepay.view.activities.MainActivity;

/**
 * Created by Nithishkumar on 3/26/2016.
 */
public class EditProfileFragment extends Fragment {

    public EditProfileFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile_update, container, false);
        //init(view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this.mainActivity = (MainActivity)context;
        //this.mainActivityCallback = mainActivity;
    }
}

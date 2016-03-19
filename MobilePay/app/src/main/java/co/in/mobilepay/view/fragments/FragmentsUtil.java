package co.in.mobilepay.view.fragments;


import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import co.in.mobilepay.R;


/**
 * Created by Nithish on 06-02-2016.
 */
public class FragmentsUtil {

    public static void replaceFragment(AppCompatActivity activity,Fragment fragment,int containerId){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();

          fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    public static void addFragment(AppCompatActivity activity,Fragment fragment,int containerId){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(containerId, fragment)
                .commit();
    }

    public static void removeFragment(AppCompatActivity activity,int containerId){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment =  fragmentManager.findFragmentById(containerId);
        if(fragment != null){
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }


    public static void backPressed(FragmentManager fragmentManager){
        if(fragmentManager.getBackStackEntryCount() > 0)
            fragmentManager.popBackStack();
    }
}

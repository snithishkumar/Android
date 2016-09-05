package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.application.MobilePayAnalytics;
import co.in.mobilepay.view.activities.NaviDrawerActivity;
import co.in.mobilepay.view.adapters.HelpListAdapter;

/**
 * Created by Nithishkumar on 6/17/2016.
 */
public class HelpFragment extends Fragment {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    private NaviDrawerActivity naviDrawerActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        naviDrawerActivity.getSupportActionBar().setTitle("Help");
        initBackButton();
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        expandableListView = (ExpandableListView)view.findViewById(R.id.fragment_help_id);

        expandableListAdapter = new HelpListAdapter(naviDrawerActivity,loadQuestion(),loadAnswers()) ;
        expandableListView.setAdapter(expandableListAdapter);
        return view;
    }


    private void initBackButton(){
        final Drawable upArrow = ContextCompat.getDrawable(naviDrawerActivity,R.drawable.ic_arrow_back_white_24dp);
        // upArrow.setColorFilter( ContextCompat.getColor(naviDrawerActivity,R.color.white), PorterDuff.Mode.SRC_ATOP);
        naviDrawerActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        naviDrawerActivity.getSupportActionBar().setHomeButtonEnabled(true);
        naviDrawerActivity.getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private List<String> loadQuestion(){
        List<String> questions = new ArrayList<>();
        questions.add(naviDrawerActivity.getResources().getString(R.string.question_1));
        questions.add(naviDrawerActivity.getResources().getString(R.string.question_2));
        questions.add(naviDrawerActivity.getResources().getString(R.string.question_3));
        return questions;
    }


    private List<String> loadAnswers(){
        List<String> answers = new ArrayList<>();
        answers.add(naviDrawerActivity.getResources().getString(R.string.answer_1));
        answers.add(naviDrawerActivity.getResources().getString(R.string.answer_2));
        answers.add(naviDrawerActivity.getResources().getString(R.string.answer_3));
        return answers;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.naviDrawerActivity = (NaviDrawerActivity)context;
    }


    @Override
    public void onResume(){
        MobilePayAnalytics.getInstance().trackScreenView("Help-F Screen");
        super.onResume();
    }
}

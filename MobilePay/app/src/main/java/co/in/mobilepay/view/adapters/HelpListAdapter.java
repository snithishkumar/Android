package co.in.mobilepay.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.view.activities.NaviDrawerActivity;

/**
 * Created by Nithishkumar on 6/17/2016.
 */
public class HelpListAdapter extends BaseExpandableListAdapter {

    private NaviDrawerActivity naviDrawerActivity;
    private List<String> questions;
    private List<String> answers;

    public HelpListAdapter( NaviDrawerActivity naviDrawerActivity,List<String> questions,List<String> answers) {
        this.naviDrawerActivity = naviDrawerActivity;
        this.questions = questions;
        this.answers = answers;
    }

    @Override
    public int getGroupCount() {
        return questions.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return answers.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return questions.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return answers.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String questions = (String)getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.naviDrawerActivity.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapt_help_question, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.adapt_help_question_id);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(questions);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String answers = (String)getChild(groupPosition,childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.naviDrawerActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapt_help_answers, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.adapt_help_answers_id);
        expandedListTextView.setText(answers);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

package co.in.mobilepay.view.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;

/**
 * Created by Nithish on 07-02-2016.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    List<String> selectedPin = new ArrayList<>(6);
    Button firstPin = null;
    Button secondPin = null;
    Button thirdPin = null;
    Button fourthPin = null;
    Button fifthPin = null;
    Button sixthPin = null;

    private Context context;

    private VerifyLoginListeners verifyLoginListeners;

   public LoginFragment(){
       this.context = getActivity();
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        verifyLoginListeners = (VerifyLoginListeners)activity;
    }

    private void addValue(String value){
        int count = selectedPin.size();
        if(count < 6){
            selectedPin.add(value);
            resetColor(count + 1,R.drawable.small_round_red);
        }
    }

    private void verifyLogin(){
        int count = selectedPin.size();
        if(count == 6){
           String data = TextUtils.join("",selectedPin);
            verifyLoginListeners.onVerify(data);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_zero:
                addValue("0");
                break;
            case R.id.log_one:
                addValue("1");
                break;
            case R.id.log_two:
                addValue("2");
                break;
            case R.id.log_three:
                addValue("3");
                break;
            case R.id.log_four:
                addValue("4");
                break;
            case R.id.log_five:
                addValue("5");
                break;
            case R.id.log_six:
                addValue("6");
                break;
            case R.id.log_seven:
                addValue("7");
                break;
            case R.id.log_eight:
                addValue("8");
                break;
            case R.id.log_nine:
                addValue("9");
                break;
            case R.id.log_del:
                int count = selectedPin.size();
                if(count > 0){
                    selectedPin.remove(count - 1);
                    resetColor(count, R.drawable.small_round_background);
                }
                break;

        }
        verifyLogin();
    }

    private void init( View view){
        Button zero = (Button)   view.findViewById(R.id.log_zero);
        Button one = (Button)  view.findViewById(R.id.log_one);
        Button two = (Button)  view.findViewById(R.id.log_two);
        Button three = (Button)  view.findViewById(R.id.log_three);
        Button four = (Button) view.findViewById(R.id.log_four);
        Button five = (Button) view.findViewById(R.id.log_five);
        Button six = (Button) view.findViewById(R.id.log_six);
        Button seven = (Button)  view.findViewById(R.id.log_seven);
        Button eight = (Button) view.findViewById(R.id.log_eight);
        Button nine = (Button) view.findViewById(R.id.log_nine);
        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);

         firstPin = (Button)   view.findViewById(R.id.log_first_value);
         secondPin = (Button)   view.findViewById(R.id.log_second_value);
         thirdPin = (Button)   view.findViewById(R.id.log_third_value);
         fourthPin = (Button)   view.findViewById(R.id.log_fourth_value);
         fifthPin = (Button)   view.findViewById(R.id.log_fifth_value);
         sixthPin = (Button)   view.findViewById(R.id.log_sixth_value);

    }



    private void resetColor(int pos,int id){
        switch (pos){
            case 1:
                firstPin.setBackground(ContextCompat.getDrawable(context,id));
                break;
            case 2:
                secondPin.setBackground(ContextCompat.getDrawable(context,id));
                break;
            case 3:
                thirdPin.setBackground(ContextCompat.getDrawable(context,id));
                break;
            case 4:
                fourthPin.setBackground(ContextCompat.getDrawable(context,id));
                break;
            case 5:
                fifthPin.setBackground(ContextCompat.getDrawable(context,id));
                break;
            case 6:
                sixthPin.setBackground(ContextCompat.getDrawable(context,id));
                break;
        }
    }

   public  interface VerifyLoginListeners{
        public void onVerify(String data);
    }


}

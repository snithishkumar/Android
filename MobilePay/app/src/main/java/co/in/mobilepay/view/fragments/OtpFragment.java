package co.in.mobilepay.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import co.in.mobilepay.R;

/**
 * Created by Nithish on 07-02-2016.
 */
public class OtpFragment extends Fragment {

    private EditText otpNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp, container, false);
        otpNumber = (EditText) view.findViewById(R.id.otp_number);
        return view;
    }

    public String getOtpNumber(){
        String optNumberTemp = otpNumber.getText().toString();
        if(optNumberTemp == null || optNumberTemp.isEmpty()){
            otpNumber.setError("Otp Should not be blank");
        }
        return optNumberTemp;
    }
}

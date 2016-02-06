package co.in.mobilepay.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import co.in.mobilepay.R;
import co.in.mobilepay.json.request.RegisterJson;

/**
 * Created by Nithish on 06-02-2016.
 */
public class RegistrationFragment extends Fragment{
    EditText name = null;
    EditText number = null;
    EditText password = null;
    EditText rePassword = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        name = (EditText) view.findViewById(R.id.reg_name);
        number = (EditText)view.findViewById(R.id.reg_number);
        password = (EditText)view.findViewById(R.id.reg_password);
        rePassword = (EditText) view.findViewById(R.id.reg_repassword);
        return view;
    }

    public RegisterJson getRegistrationJson(){
        String nameTemp = name.getText().toString();
        if(nameTemp == null){
            name.setError("Name should not be blank");
        }
        String mobileNumberTemp = number.getText().toString();
        if(nameTemp == null){
            number.setError("MobileNumber should not be blank");
        }
        String passwordTemp = password.getText().toString();
        if(nameTemp == null){
            password.setError("Password should not be blank");
        }
        String rePasswordTemp = rePassword.getText().toString();
        if(rePasswordTemp == null){
            rePassword.setError("RePassword should not be blank");
        }
        if(!passwordTemp.equals(rePasswordTemp)){
            rePassword.setError("Password and retype password are mismatched");
        }

        RegisterJson registerJson = new RegisterJson(nameTemp,mobileNumberTemp,passwordTemp,"");
        return registerJson;
    }
}

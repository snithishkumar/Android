package co.in.mobilepay.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import co.in.mobilepay.R;
import co.in.mobilepay.application.MobilePayAnalytics;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.enumeration.PaymentType;
import co.in.mobilepay.json.response.CardDetailsJson;
import co.in.mobilepay.json.response.CardJson;

/**
 * Created by Nithishkumar on 3/22/2016.
 */
public class NewCardFragment extends Fragment implements View.OnClickListener {

    private RadioButton creditCard = null;
    private RadioButton debitCard = null;
    private EditText cardNo = null;
    private EditText cardName = null;
    private EditText cardExpiry = null;
    private EditText cardCvv = null;


    public NewCardFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_new_cards, container, false);
        initView(view);
        return view;
    }



    private void initView(View view){
        creditCard =  (RadioButton)view.findViewById(R.id.pay_new_card_credit);
        debitCard = (RadioButton)view.findViewById(R.id.pay_new_card_debit);
        cardNo = (EditText)view.findViewById(R.id.pay_new_card_no);
        cardNo.addTextChangedListener(new TextWatcher() {
            int len=0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String str = cardNo.getText().toString();
                len = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(len < s.length()){
                    switch (s.length()){
                        case 4:
                        case 9:
                        case 14:
                            s.append("-");
                            break;
                    }
                }


            }
        });
        cardName =  (EditText)view.findViewById(R.id.pay_new_card_name);
        cardExpiry =  (EditText)view.findViewById(R.id.pay_new_card_expiry);
        cardExpiry.addTextChangedListener(new TextWatcher() {
            int len = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String str = cardExpiry.getText().toString();
                len = str.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(len < s.length()){
                    if(s.length() == 2){
                        s.append("/");
                    }
                }
            }
        });
        cardCvv =  (EditText)view.findViewById(R.id.pay_new_card_cvv);

        Button button = (Button) view.findViewById(R.id.pay_new_card_submit);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        CardJson cardJson = new CardJson();
        CardDetailsJson cardDetailsJson = new CardDetailsJson();
        cardJson.setCardDetails(cardDetailsJson);
        int count = 1;
        if(creditCard.isChecked()){
            cardJson.setPaymentType(PaymentType.CREDIT);
            count *= 2;
        }else if(debitCard.isChecked()){
            cardJson.setPaymentType(PaymentType.DEBIT);
            count *= 2;
        }
        if(count != 2){
            creditCard.setError("Please choose either CREDIT or DEBIT");
        }
        if(cardNo.getText().toString() == null || cardNo.getText().toString().isEmpty()){
            cardNo.setError("Please Enter Card No");
        }else{
            cardDetailsJson.setNumber(cardNo.getText().toString());
            count *= 2;
        }

        if(cardName.getText().toString() == null || cardName.getText().toString().isEmpty()){
            cardName.setError("Please Enter Card Name");
        }else {
            cardDetailsJson.setName(cardName.getText().toString());
            count *= 2;
        }

        if(cardExpiry.getText().toString() == null || cardExpiry.getText().toString().isEmpty()){
            cardExpiry.setError("Please Enter Card Expiry");
        }else {
            cardDetailsJson.setExpiryDate(cardExpiry.getText().toString());
            count *= 2;
        }

        if(cardCvv.getText().toString() == null || cardCvv.getText().toString().isEmpty() ){
            cardCvv.setError("Please Enter Card CVV");
        }else{
            cardDetailsJson.setCardCvv(cardCvv.getText().toString());
            count *= 2;
        }

        if(count == 32){
            //newSaveCardActivityCallback.success(0,cardJson);
        }

    }



}

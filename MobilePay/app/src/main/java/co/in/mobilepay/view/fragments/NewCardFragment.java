package co.in.mobilepay.view.fragments;

import android.content.Context;
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
import co.in.mobilepay.enumeration.PaymentType;
import co.in.mobilepay.json.response.CardDetailsJson;
import co.in.mobilepay.json.response.CardJson;
import co.in.mobilepay.view.PurchaseModel;
import co.in.mobilepay.view.activities.NewSaveCardActivity;

/**
 * A fragment representing a list of Items.
 * <p/>
 *
 */
public class NewCardFragment extends Fragment implements View.OnClickListener{


    private RadioButton creditCard = null;
    private RadioButton debitCard = null;
    private EditText cardNo = null;
    private EditText cardName = null;
    private EditText cardExpiry = null;

    private NewSaveCardActivity newSaveCardActivity = null;
    private NewSaveCardActivityCallback newSaveCardActivityCallback = null;

    //private


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NewCardFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_new_cards, container, false);
        initView(view);
        return view;
    }



    private void initView(View view){
        creditCard =  (RadioButton)view.findViewById(R.id.save_new_card_credit);
        debitCard = (RadioButton)view.findViewById(R.id.save_new_card_debit);
        cardNo = (EditText)view.findViewById(R.id.save_new_card_no);
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
        cardName =  (EditText)view.findViewById(R.id.save_new_card_name);
        cardExpiry =  (EditText)view.findViewById(R.id.save_new_card_expiry);
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
        Button button = (Button) view.findViewById(R.id.save_new_card_submit);
        button.setOnClickListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        newSaveCardActivityCallback = (NewSaveCardActivity)context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
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

        if(count == 16){
            newSaveCardActivityCallback.success(0,cardJson);
        }

    }


    public  interface NewSaveCardActivityCallback {
        void success(int code,Object data);
    }
}

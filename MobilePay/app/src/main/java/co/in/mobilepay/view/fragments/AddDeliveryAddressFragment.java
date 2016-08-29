package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import co.in.mobilepay.R;
import co.in.mobilepay.application.MobilePayAnalytics;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;

/**
 * Created by Nithishkumar on 4/8/2016.
 */
public class AddDeliveryAddressFragment extends Fragment implements View.OnClickListener {

    private PurchaseDetailsActivity purchaseDetailsActivity;
    private PurchaseService purchaseService;
    private ShowDeliveryAddress showDeliveryAddress;

    private int addressId = 0;
    private  AddressEntity  addressEntity;

    private EditText vName;
    private EditText vMobileNumber;
    private EditText vStreet;
    private Button vSubmit;

    private TextInputLayout nameFloatingLabel;
    private TextInputLayout mobileNumberFloatingLabel;
    private TextInputLayout streetFloatingLabel;


    private static final int REQUEST_SELECT_PLACE = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle addressIdArgs = getArguments();
        if(addressIdArgs != null){
            addressId =  addressIdArgs.getInt("addressId");
        }

        View view = inflater.inflate(R.layout.fragment_add_address, container, false);
        init(view);
        return view;
    }

    private void  init(View view) {

       /* ImageView shopBackButton = (ImageView)view.findViewById(R.id.add_address_back_button);
        final Drawable upArrow = ContextCompat.getDrawable(purchaseDetailsActivity,R.drawable.abc_ic_ab_back_mtrl_am_alpha);;
        upArrow.setColorFilter( ContextCompat.getColor(purchaseDetailsActivity,R.color.white), PorterDuff.Mode.SRC_ATOP);
        shopBackButton.setBackground(upArrow);*/

        vName = (EditText) view.findViewById(R.id.add_address_name);
        vMobileNumber = (EditText) view.findViewById(R.id.add_address_phone_number);
        vStreet = (EditText) view.findViewById(R.id.add_address_street);
        vStreet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    try {
                        Intent intent = new PlaceAutocomplete.IntentBuilder
                                (PlaceAutocomplete.MODE_OVERLAY)
                                .build(purchaseDetailsActivity);
                        startActivityForResult(intent, REQUEST_SELECT_PLACE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        vSubmit = (Button) view.findViewById(R.id.add_address_submit);

        nameFloatingLabel = (TextInputLayout) view.findViewById(R.id.input_layout_add_address_name);
        setLabelColor(nameFloatingLabel);
        mobileNumberFloatingLabel = (TextInputLayout) view.findViewById(R.id.input_layout_add_address_phone_number);
        setLabelColor(mobileNumberFloatingLabel);
        streetFloatingLabel = (TextInputLayout) view.findViewById(R.id.input_layout_add_address_street);
        setLabelColor(streetFloatingLabel);

        vSubmit.setOnClickListener(this);
        if(addressId > 0){
            loadData();
            vSubmit.setText("Update");

        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == purchaseDetailsActivity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(purchaseDetailsActivity, data);
                if(place != null){
                   String address = place.getAddress().toString();
                    vStreet.setText(address);
                    vStreet.setSelection(address.length());
                }

            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void setLabelColor(TextInputLayout nameFloatingLabel){
        nameFloatingLabel.setHintTextAppearance(R.style.FloatLabelColor);
    }

    private void loadData(){
         addressEntity = purchaseService.getAddress(addressId);
        vName.setText(addressEntity.getName());
        vMobileNumber.setText(addressEntity.getMobileNumber());
        vStreet.setText(addressEntity.getAddress());

    }


    @Override
    public void onClick(View v) {
        if(addressId < 1){
            addressEntity = new AddressEntity();
            addressEntity.setAddressUUID(ServiceUtil.generateUUID());
        }

        String nameTemp = vName.getText().toString();
        if(nameTemp == null || nameTemp.trim().isEmpty()){
            vName.setError(getString(R.string.add_delivery_address_name_error));
            vName.requestFocus();
            return;
        }
        addressEntity.setName(nameTemp);

        String newMobile = vMobileNumber.getText().toString();
        if(newMobile == null || newMobile.trim().isEmpty()){
            vMobileNumber.setError(getString(R.string.add_delivery_address_mobile_error));
            vMobileNumber.requestFocus();
            return;
        }

        if(newMobile.length() != 10){
            vMobileNumber.setError(getString(R.string.add_delivery_address_mobile_number_error));
            vMobileNumber.requestFocus();
            vMobileNumber.setSelection(newMobile.length());
            return;
        }
        addressEntity.setMobileNumber(newMobile);


        String street = vStreet.getText().toString();
        if(street == null || street.trim().isEmpty()){
            vStreet.setError(getString(R.string.add_delivery_address_street_error));
            vStreet.requestFocus();
            return;
        }

        addressEntity.setAddress(street);

        addressEntity.setLastModifiedTime(ServiceUtil.getCurrentTimeMilli());
        if(addressId < 1){
            purchaseService.saveAddress(addressEntity);
            purchaseDetailsActivity.setDefaultAddress(addressEntity);
            showDeliveryAddress.viewFragment(3);

            return;
        }else {
            purchaseService.updateAddress(addressEntity);
            purchaseDetailsActivity.setDefaultAddress(addressEntity);
            showDeliveryAddress.viewFragment(3);
            return;
        }



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.purchaseDetailsActivity = (PurchaseDetailsActivity)context;
        purchaseService = purchaseDetailsActivity.getPurchaseService();
        showDeliveryAddress = purchaseDetailsActivity;
    }


    @Override
    public void onResume() {
        MobilePayAnalytics.getInstance().trackScreenView("Add Delivery Address-F Screen");
        super.onResume();
    }

    public interface ShowDeliveryAddress{
        void viewFragment(int options);
    }
}

package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import co.in.mobilepay.R;
import co.in.mobilepay.application.MobilePayAnalytics;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.json.request.RegisterJson;
import co.in.mobilepay.json.response.AddressJson;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.view.activities.ActivityUtil;
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
    private EditText vAddress;
    private EditText vArea;
    private EditText vCity;
    private EditText vPostalCode;
    private Button vSubmit;

    private TextInputLayout nameFloatingLabel;
    private TextInputLayout mobileNumberFloatingLabel;
    private TextInputLayout streetFloatingLabel;
    private TextInputLayout addressFloatingLabel;
    private TextInputLayout areaFloatingLabel;
    private TextInputLayout cityFloatingLabel;
    private TextInputLayout postalCodeFloatingLabel;

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

        ImageView shopBackButton = (ImageView)view.findViewById(R.id.add_address_back_button);
        final Drawable upArrow = ContextCompat.getDrawable(purchaseDetailsActivity,R.drawable.abc_ic_ab_back_mtrl_am_alpha);;
        upArrow.setColorFilter( ContextCompat.getColor(purchaseDetailsActivity,R.color.white), PorterDuff.Mode.SRC_ATOP);
        shopBackButton.setBackground(upArrow);

        vName = (EditText) view.findViewById(R.id.add_address_name);
        vMobileNumber = (EditText) view.findViewById(R.id.add_address_phone_number);
        vStreet = (EditText) view.findViewById(R.id.add_address_street);
        vAddress = (EditText) view.findViewById(R.id.add_address_address);
        vArea = (EditText) view.findViewById(R.id.add_address_area);
        vCity = (EditText) view.findViewById(R.id.add_address_city);
        vPostalCode = (EditText) view.findViewById(R.id.add_address_post_code);
        vSubmit = (Button) view.findViewById(R.id.add_address_submit);

        nameFloatingLabel = (TextInputLayout) view.findViewById(R.id.input_layout_add_address_name);
        setLabelColor(nameFloatingLabel);
        mobileNumberFloatingLabel = (TextInputLayout) view.findViewById(R.id.input_layout_add_address_phone_number);
        setLabelColor(mobileNumberFloatingLabel);
        streetFloatingLabel = (TextInputLayout) view.findViewById(R.id.input_layout_add_address_street);
        setLabelColor(streetFloatingLabel);
        addressFloatingLabel = (TextInputLayout) view.findViewById(R.id.input_layout_add_address_address);
        setLabelColor(addressFloatingLabel);
        areaFloatingLabel = (TextInputLayout) view.findViewById(R.id.input_layout_add_address_area);
        setLabelColor(areaFloatingLabel);
        cityFloatingLabel = (TextInputLayout) view.findViewById(R.id.input_layout_add_address_city);
        setLabelColor(cityFloatingLabel);
        postalCodeFloatingLabel = (TextInputLayout) view.findViewById(R.id.input_layout_add_address_postal_code);
        setLabelColor(postalCodeFloatingLabel);

        vSubmit.setOnClickListener(this);
        if(addressId > 0){
            loadData();
            vSubmit.setText("Update");

        }
    }


    private void setLabelColor(TextInputLayout nameFloatingLabel){
        nameFloatingLabel.setHintTextAppearance(R.style.FloatLabelColor);
    }

    private void loadData(){
         addressEntity = purchaseService.getAddress(addressId);
        vName.setText(addressEntity.getName());
        vMobileNumber.setText(addressEntity.getMobileNumber());
        vStreet.setText(addressEntity.getStreet());
        vAddress.setText(addressEntity.getAddress());
        vArea.setText(addressEntity.getArea());
        vCity.setText(addressEntity.getCity());
        vPostalCode.setText(addressEntity.getPostalCode()+"");
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
        addressEntity.setStreet(street);


        String address = vAddress.getText().toString();
        addressEntity.setAddress(address);



        String city = vCity.getText().toString();
        if(city == null || city.trim().isEmpty()){
            vCity.setError(getString(R.string.add_delivery_address_city_error));
            vCity.requestFocus();
            return;
        }
        addressEntity.setCity(city);

        String postalCode = vPostalCode.getText().toString();
        if(postalCode == null || postalCode.trim().isEmpty()){
            vPostalCode.setError(getString(R.string.add_delivery_address_postal_error));
            vPostalCode.requestFocus();
            return;
        }
        addressEntity.setPostalCode(Long.valueOf(postalCode));

        addressEntity.setIsSynced(false);
        addressEntity.setLastModifiedTime(ServiceUtil.getCurrentTimeMilli());
        if(addressId < 1){
            purchaseService.saveAddress(addressEntity);
            showDeliveryAddress.viewFragment(3);
            return;
        }else {
            purchaseService.updateAddress(addressEntity);
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

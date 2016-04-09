package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import co.in.mobilepay.R;
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
        vName = (EditText) view.findViewById(R.id.add_address_name);
        vMobileNumber = (EditText) view.findViewById(R.id.add_address_phone_number);
        vStreet = (EditText) view.findViewById(R.id.add_address_street);
        vAddress = (EditText) view.findViewById(R.id.add_address_address);
        vArea = (EditText) view.findViewById(R.id.add_address_area);
        vCity = (EditText) view.findViewById(R.id.add_address_city);
        vPostalCode = (EditText) view.findViewById(R.id.add_address_post_code);
        vSubmit = (Button) view.findViewById(R.id.add_address_submit);
        vSubmit.setOnClickListener(this);
        if(addressId > 0){
            loadData();
            vSubmit.setText("Update");

        }
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
            vName.setError("Name should not be blank");
            return;
        }
        addressEntity.setName(nameTemp);

        String newMobile = vMobileNumber.getText().toString();
        if(newMobile == null || newMobile.trim().isEmpty()){
            vMobileNumber.setError("mobile should not be blank");
            return;
        }

        if(newMobile.length() != 10){
            vMobileNumber.setError("mobile Number must be 10 digits");
            return;
        }
        addressEntity.setMobileNumber(newMobile);


        String street = vStreet.getText().toString();
        if(street == null || street.trim().isEmpty()){
            vStreet.setError("Street Name should not be blank");
            return;
        }
        addressEntity.setStreet(street);


        String address = vAddress.getText().toString();
        addressEntity.setAddress(address);



        String city = vCity.getText().toString();
        if(city == null || city.trim().isEmpty()){
            vMobileNumber.setError("City should not be blank");
            return;
        }
        addressEntity.setCity(city);

        String postalCode = vPostalCode.getText().toString();
        if(postalCode == null || postalCode.trim().isEmpty()){
            vMobileNumber.setError("Postal code should not be blank");
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

    public interface ShowDeliveryAddress{
        void viewFragment(int options);
    }
}

package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.fragments.FragmentsUtil;

/**
 * Created by Nithishkumar on 4/10/2016.
 */
public class DeliveryAddressAdapter extends RecyclerView.Adapter<DeliveryAddressAdapter.DeliveryAddressViewHolder>{

    private List<AddressEntity> addressEntityList = new ArrayList<>();
    private  PurchaseDetailsActivity purchaseDetailsActivity;

    public DeliveryAddressAdapter(List<AddressEntity> addressEntityList,PurchaseDetailsActivity purchaseDetailsActivity){
        this.addressEntityList = addressEntityList;
        this.purchaseDetailsActivity = purchaseDetailsActivity;

    }
    @Override
    public int getItemCount() {
        return addressEntityList.size();
    }

    @Override
    public DeliveryAddressViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.adapt_delivery_address, viewGroup, false);
        return  new DeliveryAddressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeliveryAddressViewHolder deliveryAddressViewHolder, int position) {
        AddressEntity addressEntity = addressEntityList.get(position);
        deliveryAddressViewHolder.vName.setText(addressEntity.getName());
        deliveryAddressViewHolder.vMobileNumber.setText(addressEntity.getMobileNumber());

        deliveryAddressViewHolder.vAddress.setText(addressEntity.getAddress());
    }

   /* private String getAddress(AddressEntity addressEntity){


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(addressEntity.getStreet());
        if(addressEntity.getAddress() != null){
            stringBuilder.append(","+addressEntity.getAddress());
        }
        if(addressEntity.getArea() != null){
            stringBuilder.append(","+addressEntity.getArea());
        }
        if(addressEntity.getCity() != null){
            stringBuilder.append(","+addressEntity.getCity());
        }


        stringBuilder.append(" - "+addressEntity.getPostalCode());
        return  stringBuilder.toString();
    }*/


    class DeliveryAddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView vName;
        private TextView vMobileNumber;
        private TextView vAddress;
        public DeliveryAddressViewHolder(View view){
            super(view);
            vName = (TextView)view.findViewById(R.id.adapt_delivery_address_name);
            vMobileNumber = (TextView)view.findViewById(R.id.adapt_delivery_address_phone_no);
            vAddress = (TextView)view.findViewById(R.id.adapt_delivery_address_details);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AddressEntity addressEntity = addressEntityList.get(getAdapterPosition());
            purchaseDetailsActivity.getPurchaseService().updateDefaultAddress(addressEntity.getAddressId());
            purchaseDetailsActivity.setDefaultAddress(addressEntity);
            FragmentsUtil.backPressed(purchaseDetailsActivity);
            return;
        }
    }
}

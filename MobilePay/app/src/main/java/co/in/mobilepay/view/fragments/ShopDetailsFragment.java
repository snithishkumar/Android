package co.in.mobilepay.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.MerchantEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;

/**
 * Created by ramse on 14-03-2016.
 */
public class ShopDetailsFragment extends Fragment {

    private PurchaseDetailsActivity purchaseDetailsActivity;
    private PurchaseService purchaseService;
    private int purchaseId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle purchaseIdArgs = getArguments();
        if(purchaseIdArgs != null){
            purchaseId =  purchaseIdArgs.getInt("purchaseId");
        }
        PurchaseEntity purchaseEntity = purchaseService.getPurchaseDetails(purchaseId);
        MerchantEntity merchantEntity = purchaseEntity.getMerchantEntity();
        View view = inflater.inflate(R.layout.shop_details,container,false);
        initView(view,merchantEntity);
        return view;
    }

    private void initView(View view,MerchantEntity merchantEntity ){
        TextView shopName = (TextView)view.findViewById(R.id.shop_details_name);
        TextView shopArea = (TextView) view.findViewById(R.id.shop_details_area);
        TextView shopCategory = (TextView)view.findViewById(R.id.shop_details_category);
        TextView shopLandLine = (TextView) view.findViewById(R.id.shop_details_land);
        TextView shopMobile = (TextView) view.findViewById(R.id.shop_details_phone);
        TextView shopAddress = (TextView) view.findViewById(R.id.shop_details_address);
        shopName.setText(merchantEntity.getMerchantName());
        shopArea.setText(merchantEntity.getArea());
        shopLandLine.setText(merchantEntity.getLandLineNumber() > 0 ? String.valueOf(merchantEntity.getLandLineNumber()) :"N/A");
        shopMobile.setText(merchantEntity.getMobileNumber() > 0 ? String.valueOf(merchantEntity.getMobileNumber()) :"N/A");
        shopAddress.setText(merchantEntity.getMerchantAddress());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        purchaseDetailsActivity = (PurchaseDetailsActivity)context;
        purchaseService = purchaseDetailsActivity.getPurchaseService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

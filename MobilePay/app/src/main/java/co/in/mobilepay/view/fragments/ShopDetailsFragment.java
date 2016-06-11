package co.in.mobilepay.view.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.MerchantEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.model.PurchaseModel;

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

        ImageView shopBackButton = (ImageView)view.findViewById(R.id.shop_back_button);
        final Drawable upArrow = ContextCompat.getDrawable(purchaseDetailsActivity,R.drawable.abc_ic_ab_back_mtrl_am_alpha);;
        upArrow.setColorFilter( ContextCompat.getColor(purchaseDetailsActivity,R.color.white), PorterDuff.Mode.SRC_ATOP);
        shopBackButton.setBackground(upArrow);

        TextView shopName = (TextView)view.findViewById(R.id.shop_details_name);
        TextView shopArea = (TextView) view.findViewById(R.id.shop_details_area);
        TextView shopCategory = (TextView)view.findViewById(R.id.shop_details_category);
        TextView shopLandLine = (TextView) view.findViewById(R.id.shop_details_land);
        TextView shopMobile = (TextView) view.findViewById(R.id.shop_details_phone);
        TextView shopAddress = (TextView) view.findViewById(R.id.shop_details_address);
        shopName.setText(merchantEntity.getMerchantName());
        shopArea.setText(merchantEntity.getArea());
        shopLandLine.setText(merchantEntity.getLandLineNumber() > 0 ? String.valueOf(merchantEntity.getLandLineNumber()) :"N/A");
        setCall(shopLandLine,merchantEntity.getLandLineNumber());
        shopMobile.setText(merchantEntity.getMobileNumber() > 0 ? String.valueOf(merchantEntity.getMobileNumber()) :"N/A");
        setCall(shopMobile,merchantEntity.getMobileNumber());
        shopAddress.setText(merchantEntity.getMerchantAddress());
    }

    private void setCall(TextView shopMobile,final long phoneNumber){
        if(phoneNumber > 0){
            shopMobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    purchaseDetailsActivity.makeCall(String.valueOf(phoneNumber));

                  /*  Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" +phoneNumber));
                    try {
                        purchaseDetailsActivity.startActivity(callIntent);  // TODO -- Need to handle request
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                }
            });

        }

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

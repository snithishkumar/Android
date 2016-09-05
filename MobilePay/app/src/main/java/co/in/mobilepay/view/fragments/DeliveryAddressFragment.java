package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.application.MobilePayAnalytics;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.adapters.DeliveryAddressAdapter;

/**
 * Created by Nithishkumar on 4/10/2016.
 */
public class DeliveryAddressFragment extends Fragment {

    private PurchaseDetailsActivity purchaseDetailsActivity;
    private DeliveryAddressAdapter deliveryAddressAdapter;
    private List<AddressEntity> addressEntityList = new ArrayList<>();

    public DeliveryAddressFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_delivery_address, container, false);

      /*  ImageView shopBackButton = (ImageView)view.findViewById(R.id.address_list_back_button);
        final Drawable upArrow = ContextCompat.getDrawable(purchaseDetailsActivity,R.drawable.abc_ic_ab_back_mtrl_am_alpha);;
        upArrow.setColorFilter( ContextCompat.getColor(purchaseDetailsActivity,R.color.white), PorterDuff.Mode.SRC_ATOP);
        shopBackButton.setBackground(upArrow);*/

        RecyclerView  recyclerView = (RecyclerView) view.findViewById(R.id.fragment_delivery_addr_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        setAdapters(recyclerView);
        getAddressModel();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        purchaseDetailsActivity = (PurchaseDetailsActivity)context;
    }

    private void setAdapters(RecyclerView recyclerView){
        deliveryAddressAdapter = new DeliveryAddressAdapter(addressEntityList,purchaseDetailsActivity);
        recyclerView.setAdapter(deliveryAddressAdapter);
    }

    private void getAddressModel(){
        List<AddressEntity> addressEntityList =  purchaseDetailsActivity.getPurchaseService().getAddressList();
        this.addressEntityList.clear();
        this.addressEntityList.addAll(addressEntityList);
        deliveryAddressAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        MobilePayAnalytics.getInstance().trackScreenView("Delivery Address List-F Screen");
        super.onResume();
    }
}

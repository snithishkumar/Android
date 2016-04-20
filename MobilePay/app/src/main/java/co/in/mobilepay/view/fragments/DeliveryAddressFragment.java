package co.in.mobilepay.view.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.sync.MobilePaySyncAdapter;
import co.in.mobilepay.view.activities.HomeActivity;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.adapters.DeliveryAddressAdapter;
import co.in.mobilepay.view.adapters.PurchaseListAdapter;
import co.in.mobilepay.view.model.PurchaseModel;

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
}
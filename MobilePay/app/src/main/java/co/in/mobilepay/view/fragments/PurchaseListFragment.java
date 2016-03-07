package co.in.mobilepay.view.fragments;

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
import co.in.mobilepay.view.model.PurchaseModel;
import co.in.mobilepay.view.activities.HomeActivity;
import co.in.mobilepay.view.adapters.PurchaseListAdapter;

public class PurchaseListFragment extends Fragment  {


   private HomeActivity homeActivity = null;
    private SwipeRefreshLayout refreshLayout;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    public PurchaseListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity = (HomeActivity)getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_purchase_list, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.purchase_list_swipe);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Need to Call Service class TODO
                homeActivity.getPurchaseService().syncPurchaseData();
            }
        });

         recyclerView = (RecyclerView) view.findViewById(R.id.purchase_list_fragment);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager =  new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                refreshLayout.setEnabled(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });
        setAdapters(recyclerView);
        return view;
    }


    private void setAdapters(RecyclerView recyclerView){
        List<PurchaseModel> purchaseModelList =  homeActivity.getPurchaseService().getCurrentPurchase();
        PurchaseListAdapter purchaseListAdapter = new PurchaseListAdapter(purchaseModelList,homeActivity);
        recyclerView.setAdapter(purchaseListAdapter);
    }


}

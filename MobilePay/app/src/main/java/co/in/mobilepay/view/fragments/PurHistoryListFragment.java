package co.in.mobilepay.view.fragments;

import android.accounts.Account;
import android.content.ContentResolver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.sync.MobilePaySyncAdapter;
import co.in.mobilepay.view.activities.HomeActivity;
import co.in.mobilepay.view.adapters.LuggageListAdapter;
import co.in.mobilepay.view.adapters.PurHistoryListAdapter;
import co.in.mobilepay.view.model.PurchaseModel;

public class PurHistoryListFragment extends Fragment  {


   private HomeActivity homeActivity = null;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    public PurHistoryListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity = (HomeActivity)getActivity();
       // syncData();

    }

    private void syncData(){
        Account account = MobilePaySyncAdapter.getSyncAccount(homeActivity);

        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);

        ContentResolver.requestSync(account, getString(R.string.auth_type), settingsBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_purchase_history_list, container, false);



      //  refreshLayout.

         recyclerView = (RecyclerView) view.findViewById(R.id.purchase_history_list);
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
            }
        });
        setAdapters(recyclerView);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }





    private void setAdapters(RecyclerView recyclerView){
        List<PurchaseModel> purchaseModelList =  homeActivity.getPurchaseService().getCurrentPurchase();
        PurHistoryListAdapter purHistoryListAdapter = new PurHistoryListAdapter(purchaseModelList,homeActivity);
        recyclerView.setAdapter(purHistoryListAdapter);
    }







}

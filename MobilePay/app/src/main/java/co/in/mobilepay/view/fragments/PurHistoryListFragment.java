package co.in.mobilepay.view.fragments;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.bus.PurchaseListPoster;
import co.in.mobilepay.sync.MobilePaySyncAdapter;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.HomeActivity;
import co.in.mobilepay.view.activities.MainActivity;
import co.in.mobilepay.view.adapters.PurHistoryListAdapter;
import co.in.mobilepay.view.model.PurchaseModel;

public class PurHistoryListFragment extends Fragment  {


   private HomeActivity homeActivity = null;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private PurHistoryListAdapter purHistoryListAdapter;
    private SwipeRefreshLayout refreshLayout;
    List<PurchaseModel> purchaseModelList = new ArrayList<>();


    private  Account account;
    private   Bundle settingsBundle;


    public PurHistoryListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity = (HomeActivity)getActivity();
        init();


    }

    private void init(){
        account = MobilePaySyncAdapter.getSyncAccount(homeActivity);

        settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putInt("currentTab", 3);

    }

    private void syncData(){
        ContentResolver.requestSync(account, getString(R.string.auth_type), settingsBundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_purchase_history_list, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.purchase_history_list_swipe);

        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Need to Call Service class TODO
                syncData();
            }
        });


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
                refreshLayout.setEnabled(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });
        setAdapters(recyclerView);
        getPurchaseModel();
        return view;
    }

    @Override
    public void onPause() {
        MobilePayBus.getInstance().unregister(this);
        stopRefreshing();
        super.onPause();
    }

    @Override
    public void onResume(){
        MobilePayBus.getInstance().register(this);
        super.onResume();
    }




    private void setAdapters(RecyclerView recyclerView){
        purHistoryListAdapter = new PurHistoryListAdapter(purchaseModelList,homeActivity);
        recyclerView.setAdapter(purHistoryListAdapter);
    }


    private void getPurchaseModel(){
        List<PurchaseModel> purchaseModelList =  homeActivity.getPurchaseService().getPurchaseHistoryList();
        this.purchaseModelList.clear();
        this.purchaseModelList.addAll(purchaseModelList);
        purHistoryListAdapter.notifyDataSetChanged();
    }

    private void showLoginActivity(){
        Intent intent = new Intent(homeActivity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        homeActivity.finish();
    }


    @Subscribe
    public void purchaseResponse(PurchaseListPoster purchaseListPoster){
        stopRefreshing();
        if(purchaseListPoster.getStatusCode() == 200){
            getPurchaseModel();
        }else if(purchaseListPoster.getStatusCode() == 10){
            showLoginActivity();
        }else{
            ActivityUtil.toast(homeActivity, getString(R.string.error_purchase_list));
        }

    }

    private void stopRefreshing(){
        if(refreshLayout != null){
            refreshLayout.setRefreshing(false);
        }
    }


}

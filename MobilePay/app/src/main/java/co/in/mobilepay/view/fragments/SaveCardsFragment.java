package co.in.mobilepay.view.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.json.response.CardJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.NaviDrawerActivity;
import co.in.mobilepay.view.adapters.SaveCardsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class SaveCardsFragment extends Fragment implements SaveCardsAdapter.OnItemLongClickListener{

    private NaviDrawerActivity naviDrawerActivity;
    private ProgressDialog progressDialog = null;
    private Gson gson = null;
    private  RecyclerView recyclerView = null;
    private List<CardJson> cardJsonList = new ArrayList<>();
    private SaveCardsAdapter saveCardsAdapter;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SaveCardsFragment() {
        gson = new Gson();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        naviDrawerActivity.getSupportActionBar().setTitle("Save Card List");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_card_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.shop_product_items_view);
        saveCardsAdapter = new SaveCardsAdapter(cardJsonList,this);
        recyclerView.setAdapter(saveCardsAdapter);

        boolean isNet = ServiceUtil.isNetworkConnected(naviDrawerActivity);
        if(isNet){
            progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", naviDrawerActivity);
            naviDrawerActivity.getCardService().getCardList();
        }else{

            ActivityUtil.showDialog(naviDrawerActivity, "No Network", "Check your connection.");
        }

        return view;
    }



    @Override
    public void onPause() {
        MobilePayBus.getInstance().unregister(this);
        super.onPause();
    }

    @Override
    public void onResume(){
        MobilePayBus.getInstance().register(this);
        super.onResume();
    }

    @Subscribe
    public void saveCardResponse(ResponseData responseData){
        if(progressDialog != null){
            progressDialog.dismiss();
        }

        if(responseData.getStatusCode() == MessageConstant.CARD_LIST_SUCCESS){
            String cardDetails = responseData.getData();
           List<CardJson> cardJsonList =  gson.fromJson(cardDetails, new TypeToken<List<CardJson>>() {
           }.getType());

            this.cardJsonList.clear();
            this.cardJsonList.addAll(cardJsonList);
            saveCardsAdapter.notifyDataSetChanged();
        }

    }



    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.naviDrawerActivity = (NaviDrawerActivity)context;
    }

    @Override
    public boolean onItemLongClicked(final String cardGuid) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(naviDrawerActivity);

        // Setting Dialog Title
        alertDialog.setTitle("Delete");

        // Setting Dialog Message
        alertDialog.setMessage("Are you Sure to delete");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                removeCard(cardGuid);

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
        return true;
    }

    private void removeCard(String cardGuid){
        boolean isNet = ServiceUtil.isNetworkConnected(naviDrawerActivity);
        if(isNet){
            progressDialog = ActivityUtil.showProgress("In Progress", "Authenticating...", naviDrawerActivity);
            CardJson cardJson = new CardJson();
            cardJson.setCardGuid(cardGuid);
            naviDrawerActivity.getCardService().removeCard(cardJson);
        }else{

            ActivityUtil.showDialog(naviDrawerActivity, "No Network", "Check your connection.");
        }

    }
}

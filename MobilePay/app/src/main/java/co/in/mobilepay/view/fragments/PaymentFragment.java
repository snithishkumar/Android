package co.in.mobilepay.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.otto.Subscribe;

import co.in.mobilepay.R;
import co.in.mobilepay.bus.MobilePayBus;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.json.response.CardJson;
import co.in.mobilepay.json.response.ResponseData;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.MessageConstant;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.HomeActivity;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.adapters.PaySaveCardsAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class PaymentFragment extends Fragment {

    private int purchaseId = 0;

    private TextView shopName = null;
    private TextView totalAmount = null;
    private RecyclerView recyclerView = null;
    private PurchaseDetailsActivity purchaseDetailsActivity = null;
    private ProgressDialog progressDialog = null;
    private Gson gson = null;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PaymentFragment() {
        gson = new Gson();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle purchaseIdArgs = getArguments();
        if(purchaseIdArgs != null){
            purchaseId =  purchaseIdArgs.getInt("purchaseId");
        }
        View view = inflater.inflate(R.layout.fragment_pay_options, container, false);
        initView(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.pay_save_card_list);
        boolean isNet = ServiceUtil.isNetworkConnected(purchaseDetailsActivity);
        if(isNet){
            progressDialog = ActivityUtil.showProgress("In Progress", "Loading...", purchaseDetailsActivity);
            purchaseDetailsActivity.getCardService().getCardList();
        }else{

            ActivityUtil.showDialog(purchaseDetailsActivity, "No Network", "Check your connection.");
        }

        return view;
    }


    /**
     * Initialize
     * @param view
     */
    private void initView(View view){
        PurchaseEntity purchaseEntity = purchaseDetailsActivity.getPurchaseService().getPurchaseDetails(purchaseId);
        totalAmount   = (TextView) view.findViewById(R.id.pay_total_amt);
        totalAmount.setText( getResources().getString(R.string.indian_rupee_symbol)+purchaseEntity.getTotalAmount());
        shopName =  (TextView) view.findViewById(R.id.pay_shop_name);
        shopName.setText("For "+purchaseEntity.getMerchantEntity().getMerchantName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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
            CardJson cardJson = new CardJson();
            cardJson.setCardGuid("");
            cardJsonList.add(cardJson);
            int size = (80*cardJsonList.size())+80;
            refreshListviewHeight(size);
            recyclerView.setAdapter(new PaySaveCardsAdapter(purchaseDetailsActivity,cardJsonList,this));
        }

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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.purchaseDetailsActivity = (PurchaseDetailsActivity)context;
    }
    public void refreshListviewHeight(int size){
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size);
        recyclerView.setLayoutParams(lp);
    }

}

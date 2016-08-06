package co.in.mobilepay.view.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import co.in.mobilepay.R;
import co.in.mobilepay.application.MobilePayAnalytics;
import co.in.mobilepay.entity.MerchantEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.enumeration.DeliveryOptions;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.util.MobilePayUtil;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.adapters.MobilePayDividerItemDetoration;
import co.in.mobilepay.view.adapters.ProductDetailsAdapter;
import co.in.mobilepay.view.model.AmountDetailsJson;
import co.in.mobilepay.view.model.ProductDetailsModel;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class ProductsDetailsFragment extends Fragment implements View.OnClickListener{

    private PurchaseDetailsActivity purchaseDetailsActivity;
    private PurchaseService purchaseService;
    private ProductDetailsAdapter productDetailsAdapter;
    private int purchaseId = 0;

    private TextView shopName = null;
    private TextView shopArea = null;
    private TextView shopOrderId = null;
    private TextView shoppingDateTime = null;
    private Button cancel = null;
    private Button submit = null;

    private String reasonToDecline = null;

    AlertDialog alertDialogBox = null;


    private Gson gson = null;

    private  PurchaseEntity  purchaseEntity= null;
    private List<ProductDetailsModel> productDetailsModelList = null;


    ProgressDialog progressDialog = null;
    ShowPaymentOptions showPaymentOptions;




    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductsDetailsFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(gson == null){
            gson = new Gson();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle purchaseIdArgs = getArguments();
        if(purchaseIdArgs != null && purchaseEntity == null){
            purchaseId =  purchaseIdArgs.getInt("purchaseId");
            loadData();
        }
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        initView(view);
        populatePurchaseData(view);
        return view;
    }

    /**
     * Initialize
     * @param view
     */
    private void initView(View view){

        shopName = (TextView)view.findViewById(R.id.shop_name);
        shopArea = (TextView)view.findViewById(R.id.shop_area);
        shoppingDateTime  = (TextView)view.findViewById(R.id.shop_date_time);
        shopOrderId = (TextView)view.findViewById(R.id.shop_order_id);
        cancel = (Button) view.findViewById(R.id.shop_details_cancel);
        cancel.setOnClickListener(this);
        submit = (Button) view.findViewById(R.id.shop_details_pay);
        submit.setOnClickListener(this);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        purchaseDetailsActivity = (PurchaseDetailsActivity)context;
        showPaymentOptions = purchaseDetailsActivity;
        purchaseService = purchaseDetailsActivity.getPurchaseService();
    }

    /**
     * Populate value
     */
    private void populatePurchaseData(View view){

        MerchantEntity merchantEntity = purchaseEntity.getMerchantEntity();
        shopName.setText(merchantEntity.getMerchantName());
        shopArea.setText("("+merchantEntity.getArea()+")");
        shopOrderId.append( purchaseEntity.getBillNumber());
        String purchaseDateTime =  MobilePayUtil.formatDate(purchaseEntity.getPurchaseDateTime());
        shoppingDateTime.setText(purchaseDateTime);

        // Set the adapter
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.shop_product_items_view);
        String amountDetails = purchaseEntity.getAmountDetails();
        AmountDetailsJson amountDetailsJson = gson.fromJson(amountDetails, AmountDetailsJson.class);
        productDetailsAdapter = new ProductDetailsAdapter(purchaseDetailsActivity,productDetailsModelList,amountDetailsJson,purchaseEntity);
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(purchaseDetailsActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(productDetailsAdapter);
        recyclerView.addItemDecoration(new MobilePayDividerItemDetoration(
                getContext()
        ));
    }

    private void loadData(){
        purchaseEntity = purchaseService.getPurchaseDetails(purchaseId);
        String productDetails = purchaseEntity.getProductDetails();

        productDetailsModelList = gson.fromJson(productDetails, new TypeToken<List<ProductDetailsModel>>() {
        }.getType());
    }


    private void declineData(){
        purchaseEntity.setProductDetails(gson.toJson(productDetailsModelList));
        purchaseEntity.setCalculatedAmountDetails(gson.toJson(productDetailsAdapter.getCalculatedAmounts()));
        purchaseDetailsActivity.getPurchaseService().declinePurchase(purchaseEntity, reasonToDecline);
        purchaseDetailsActivity.syncPaymentData();
        purchaseDetailsActivity.onBackPressed();

    }


    private void showFeedBackWindow(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(purchaseDetailsActivity);
        LayoutInflater inflater = purchaseDetailsActivity.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_decline_feedback, null);
        alertDialog.setView(dialogView);
        RadioGroup radioGroup = (RadioGroup)  dialogView.findViewById(R.id.alert_decline_feedback_message);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.alert_decline_feedback_one:
                        reasonToDecline = purchaseDetailsActivity.getResources().getString(R.string.alert_decline_feedback_one);
                        break;
                    case R.id.alert_decline_feedback_two:
                        reasonToDecline = purchaseDetailsActivity.getResources().getString(R.string.alert_decline_feedback_two);
                        break;
                    case R.id.alert_decline_feedback_three:
                        reasonToDecline = purchaseDetailsActivity.getResources().getString(R.string.alert_decline_feedback_three);
                        break;
                    case R.id.alert_decline_feedback_four:
                        reasonToDecline = purchaseDetailsActivity.getResources().getString(R.string.alert_decline_feedback_four);
                        break;
                }
            }
        });
        //
        cancel = (Button)dialogView.findViewById(R.id.alert_decline_feedback_back);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBox.dismiss();
            }
        });
        submit = (Button)dialogView.findViewById(R.id.alert_decline_feedback_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reasonToDecline != null){
                    alertDialogBox.dismiss();
                    declineData();
                }else{
                    ActivityUtil.toast(purchaseDetailsActivity,"Please select any one reason.");
                }

            }
        });
        dialogView.findViewById(R.id.alert_decline_feedback_submit);


        // Showing Alert Message
        alertDialogBox = alertDialog.show();
    }




    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.shop_details_cancel:
        showFeedBackWindow();
        break;

    case R.id.shop_details_pay:
        makePayment();
        break;
    case R.id.alert_decline_feedback_back:

        break;
    case R.id.alert_decline_feedback_submit:

        break;
}
    }

    private void makePayment(){
        if(purchaseEntity.getMerchantDeliveryOptions().ordinal() == DeliveryOptions.NONE.ordinal()){
            purchaseEntity.setUserDeliveryOptions(DeliveryOptions.NONE);
            purchaseDetailsActivity.setDeliveryOptions(DeliveryOptions.NONE);
        }
        if(purchaseDetailsActivity.getDeliveryOptions() != null){
            purchaseEntity.setUserDeliveryOptions(purchaseDetailsActivity.getDeliveryOptions());
            purchaseEntity.setCalculatedAmountDetails(gson.toJson(productDetailsAdapter.getCalculatedAmounts()));
            purchaseEntity.setProductDetails(gson.toJson(productDetailsModelList));
            if(purchaseDetailsActivity.getDeliveryOptions().ordinal() == DeliveryOptions.HOME.ordinal()){
                purchaseEntity.setAddressEntity(purchaseDetailsActivity.getDefaultAddress());
            }

            purchaseEntity.setLastModifiedDateTime(ServiceUtil.getCurrentTimeMilli());
            purchaseService.updatePurchaseEntity(purchaseEntity);
            showPaymentOptions.viewFragment(4);
            return;
        } else {
            ActivityUtil.toast(purchaseDetailsActivity, "Please select at least one Delivery Option.");
        }
    }

    @Override
    public void onResume(){
        MobilePayAnalytics.getInstance().trackScreenView("Purchase Bag Product  Details-F Screen");
        super.onResume();
    }


   public interface ShowPaymentOptions{
        void viewFragment(int options);
    }
}

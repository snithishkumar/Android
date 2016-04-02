package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.MerchantEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.util.MobilePayUtil;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.adapters.MobilePayDividerItemDetoration;
import co.in.mobilepay.view.adapters.ProductDetailsRecyclerAdapter;
import co.in.mobilepay.view.model.ProductDetailsModel;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class ProductsDetailsFragment extends Fragment {

    private PurchaseDetailsActivity purchaseDetailsActivity;
    private PurchaseService purchaseService;
    private int purchaseId = 0;

    private TextView shopName = null;
    private TextView shopArea = null;
    private TextView shopLnNo = null;
    private TextView shopPnNo = null;
    private TextView billNo = null;
    private TextView purDateTime = null;

    private Gson gson = null;

    private  PurchaseEntity  purchaseEntity= null;
    private List<ProductDetailsModel> productDetailsModelList = null;

    private int totalProduct = 0;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductsDetailsFragment() {

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
        View view = inflater.inflate(R.layout.fragment_product_list_update, container, false);
        initView(view);
        populatePurchaseData(view);
        return view;
    }

    /**
     * Initialize
     * @param view
     */
    private void initView(View view){
        if(gson == null){
            gson = new Gson();
        }
        shopName = (TextView)view.findViewById(R.id.shop_name);
        shopArea = (TextView)view.findViewById(R.id.shop_area);
        shopLnNo = (TextView)view.findViewById(R.id.shop_land_no);
        shopPnNo = (TextView)view.findViewById(R.id.shop_land_ph);
        billNo = (TextView) view.findViewById(R.id.bill_no);
        purDateTime = (TextView)view.findViewById(R.id.purchase_date_time);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        purchaseDetailsActivity = (PurchaseDetailsActivity)context;
        purchaseService = purchaseDetailsActivity.getPurchaseService();
    }

    /**
     * Populate value
     */
    private void populatePurchaseData(View view){
         purchaseEntity = purchaseService.getPurchaseDetails(purchaseId);
        MerchantEntity merchantEntity = purchaseEntity.getMerchantEntity();
        shopName.setText(merchantEntity.getMerchantName());
        shopArea.setText(merchantEntity.getArea());
        shopLnNo.setText("Ln:"+merchantEntity.getLandLineNumber());
        shopPnNo.setText("Pn:"+merchantEntity.getMobileNumber());
        billNo.setText("Bill No:" + purchaseEntity.getBillNumber());
        String purchaseDateTime =  MobilePayUtil.formatDate(purchaseEntity.getPurchaseDateTime());
        purDateTime.setText("Date:"+purchaseDateTime);

        String productDetails = purchaseEntity.getProductDetails();

       productDetailsModelList = gson.fromJson(productDetails, new TypeToken<List<ProductDetailsModel>>() {
        }.getType());

        totalProduct = productDetailsModelList.size();
        // Set the adapter
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.save_card_list);
        recyclerView.setAdapter(new ProductDetailsRecyclerAdapter(purchaseDetailsActivity,productDetailsModelList));
        recyclerView.addItemDecoration(new MobilePayDividerItemDetoration(
                getContext()
        ));
    }


    private void declineData(){
        if(totalProduct != productDetailsModelList.size()){
            purchaseService.updatePurchaseData(purchaseEntity,productDetailsModelList);
        }
    }




    @Override
    public void onDetach() {
        super.onDetach();
    }


}

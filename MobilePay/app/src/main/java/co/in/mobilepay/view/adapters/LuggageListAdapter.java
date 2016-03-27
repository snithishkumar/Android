package co.in.mobilepay.view.adapters;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.view.activities.HomeActivity;
import co.in.mobilepay.view.model.PurchaseModel;

/**
 * Created by Nithish on 16-02-2016.
 */
public class LuggageListAdapter extends RecyclerView.Adapter<LuggageListAdapter.LuggageListViewHolder> {

    private List<PurchaseModel> purchaseModelList;

    private int position;
    PurchaseListClickListeners purchaseListClickListeners;
    private HomeActivity homeActivity;

    public LuggageListAdapter(List<PurchaseModel> purchaseModelList, HomeActivity homeActivity){
        this.purchaseModelList = purchaseModelList;
        this.homeActivity = homeActivity;
    }

    @Override
    public int getItemCount() {
        return purchaseModelList.size();
    }



    @Override
    public LuggageListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       final View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.adapt_luggage_list, viewGroup, false);
      /*  itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseModel purchaseModel = purchaseModelList.get(position);
                int purchaseId = purchaseModel.getPurchaseId();
               // itemView.setFocusable(true);
                purchaseListClickListeners.purchaseListOnClick(purchaseId);
                //
            }
        });*/



        return new LuggageListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LuggageListViewHolder luggageListViewHolder, int position) {
        this.position = position;
       final PurchaseModel purchaseModel =  purchaseModelList.get(position);
        luggageListViewHolder.vBillNumber.setText("Order Id: "+purchaseModel.getBillNumber());
        luggageListViewHolder.vName.setText("Shop: "+purchaseModel.getName()+","+purchaseModel.getArea());
        luggageListViewHolder.vPurchaseDateTime.setText(ServiceUtil.getDateTimeAsString(purchaseModel.getDateTime()));
        luggageListViewHolder.vCategory.setText("Category: "+purchaseModel.getCategory());
        luggageListViewHolder.vTotalAmount.setText(homeActivity.getResources().getString(R.string.indian_rupee_symbol)+""+purchaseModel.getTotalAmount());
        luggageListViewHolder.vCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + purchaseModel.getContactNumber()));
              try{
                  homeActivity.startActivity(callIntent);  // TODO -- Need to handle request
              }catch (Exception e){
                    e.printStackTrace();
              }

            }
        });

    }

    class LuggageListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vPurchaseDateTime;
        protected TextView vBillNumber;
        protected TextView vTotalAmount;
        protected TextView vName;
        protected TextView vCategory;
        protected TextView vCall;
        protected TextView vLuggageStatus;



        public LuggageListViewHolder(View view){
            super(view);
            vPurchaseDateTime = (TextView) view.findViewById(R.id.luggage_pur_date);
            vBillNumber = (TextView) view.findViewById(R.id.luggage_pur_bill_no);
            vTotalAmount = (TextView) view.findViewById(R.id.luggage_pur_total_amt);
            vName = (TextView) view.findViewById(R.id.luggage_pur_shop_name);
            vCategory = (TextView) view.findViewById(R.id.luggage_pur_shop_category);
            vCall = (TextView) view.findViewById(R.id.luggage_pur_call);
            vLuggageStatus = (TextView) view.findViewById(R.id.luggage_pur_status);

        }
    }

    public interface PurchaseListClickListeners{
         void purchaseListOnClick(int purchaseId);
    }
}

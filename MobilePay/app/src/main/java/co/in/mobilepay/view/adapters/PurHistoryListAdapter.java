package co.in.mobilepay.view.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
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
public class PurHistoryListAdapter extends RecyclerView.Adapter<PurHistoryListAdapter.PurHistoryListViewHolder> {

    private List<PurchaseModel> purchaseModelList;

    private int position;
    PurchaseListClickListeners purchaseListClickListeners;
    private HomeActivity homeActivity;

    public PurHistoryListAdapter(List<PurchaseModel> purchaseModelList, HomeActivity homeActivity){
        this.purchaseModelList = purchaseModelList;
        this.homeActivity = homeActivity;
    }

    @Override
    public int getItemCount() {
        return purchaseModelList.size();
    }



    @Override
    public PurHistoryListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       final View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.adapt_purchase_history_list, viewGroup, false);
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



        return new PurHistoryListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PurHistoryListViewHolder purHistoryListViewHolder, int position) {
        this.position = position;
       final PurchaseModel purchaseModel =  purchaseModelList.get(position);
        purHistoryListViewHolder.vBillNumber.setText("Bill No: "+purchaseModel.getBillNumber());
        purHistoryListViewHolder.vName.setText("Shop: "+purchaseModel.getName()+","+purchaseModel.getArea());
        purHistoryListViewHolder.vPurchaseDateTime.setText(ServiceUtil.getDateTimeAsString(purchaseModel.getDateTime()));
        purHistoryListViewHolder.vTotalAmount.setText(homeActivity.getResources().getString(R.string.indian_rupee_symbol)+""+purchaseModel.getTotalAmount());


    }

    class PurHistoryListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vPurchaseDateTime;
        protected TextView vBillNumber;
        protected TextView vTotalAmount;
        protected TextView vName;



        public PurHistoryListViewHolder(View view){
            super(view);
            vPurchaseDateTime = (TextView) view.findViewById(R.id.adapt_pur_his_date);
            vBillNumber = (TextView) view.findViewById(R.id.adapt_pur_his_bill_no);
            vTotalAmount = (TextView) view.findViewById(R.id.adapt_pur_his_amount);
            vName = (TextView) view.findViewById(R.id.adapt_pur_his_shop_name);

        }
    }

    public interface PurchaseListClickListeners{
         void purchaseListOnClick(int purchaseId);
    }
}

package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.view.activities.HomeActivity;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.model.PurchaseModel;

/**
 * Created by Nithish on 16-02-2016.
 */
public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.PurchaseListViewHolder> {

    private List<PurchaseModel> purchaseModelList;

    private int position;
    PurchaseListClickListeners purchaseListClickListeners;
    private HomeActivity homeActivity;

    public PurchaseListAdapter( List<PurchaseModel> purchaseModelList,HomeActivity homeActivity){
        this.purchaseModelList = purchaseModelList;
        this.homeActivity = homeActivity;
        this.purchaseListClickListeners = homeActivity;
    }

    @Override
    public int getItemCount() {
        return purchaseModelList.size();
    }



    @Override
    public PurchaseListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
       final View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.purchase_list, viewGroup, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseModel purchaseModel = purchaseModelList.get(position);
                int purchaseId = purchaseModel.getPurchaseId();
               // itemView.setFocusable(true);
                purchaseListClickListeners.purchaseListOnClick(purchaseId);
                //
            }
        });

        return new PurchaseListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PurchaseListViewHolder purchaseListViewHolder, int position) {
        this.position = position;
        PurchaseModel purchaseModel =  purchaseModelList.get(position);
        purchaseListViewHolder.vPurchaseDateTime.setText(ServiceUtil.getDateTimeAsString(purchaseModel.getDateTime()));
        purchaseListViewHolder.vCategory.setText(purchaseModel.getCategory());
        purchaseListViewHolder.vBillNumber.setText(purchaseModel.getBillNumber());
        purchaseListViewHolder.vTotalAmount.setText(homeActivity.getResources().getString(R.string.indian_rupee_symbol)+""+purchaseModel.getTotalAmount());
        purchaseListViewHolder.vName.setText(purchaseModel.getName()+","+purchaseModel.getArea());
    }

    class PurchaseListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vPurchaseDateTime;
        protected TextView vCategory;
        protected TextView vBillNumber;
        protected TextView vTotalAmount;
        protected TextView vName;


        public PurchaseListViewHolder(View view){
            super(view);
            vPurchaseDateTime = (TextView) view.findViewById(R.id.pur_lst_date);
            vCategory = (TextView) view.findViewById(R.id.pur_lst_category);
            vBillNumber = (TextView) view.findViewById(R.id.pur_lst_bill_number);
            vTotalAmount = (TextView) view.findViewById(R.id.pur_lst_amount);
            vName = (TextView) view.findViewById(R.id.pur_lst_shop_name);
        }
    }

    public interface PurchaseListClickListeners{
         void purchaseListOnClick(int purchaseId);
    }
}

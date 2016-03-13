package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.view.model.PurchaseModel;

/**
 * Created by Nithish on 16-02-2016.
 */
public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.PurchaseListViewHolder> {

    private List<PurchaseModel> purchaseModelList;

    private int position;
    PurchaseListClickListeners purchaseListClickListeners;

    public PurchaseListAdapter( List<PurchaseModel> purchaseModelList,PurchaseListClickListeners purchaseListClickListeners){
        this.purchaseModelList = purchaseModelList;
        this.purchaseListClickListeners = purchaseListClickListeners;
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
        purchaseListViewHolder.vName.setText(purchaseModel.getName());
        purchaseListViewHolder.vCategory.setText("Category :"+purchaseModel.getCategory());
        purchaseListViewHolder.vTotalAmount.setText("Total Amount :"+purchaseModel.getTotalAmount());
    }

    class PurchaseListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vName;
        protected TextView vCategory;
        protected TextView vTotalAmount;

        public PurchaseListViewHolder(View view){
            super(view);
            vName = (TextView) view.findViewById(R.id.pur_lst_name);
            vCategory = (TextView) view.findViewById(R.id.pur_lst_category);
            vTotalAmount = (TextView) view.findViewById(R.id.pur_lst_amount);

        }
    }

    public interface PurchaseListClickListeners{
         void purchaseListOnClick(int purchaseId);
    }
}

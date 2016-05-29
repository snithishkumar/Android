package co.in.mobilepay.view.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.enumeration.OrderStatus;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.sync.ServiceAPI;
import co.in.mobilepay.view.activities.HomeActivity;
import co.in.mobilepay.view.model.PurchaseModel;

/**
 * Created by Nithish on 16-02-2016.
 */
public class PurHistoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PurchaseModel> purchaseModelList;

    PurchaseListClickListeners purchaseListClickListeners;
    private HomeActivity homeActivity;

    private static final int EMPTY_VIEW = -1;

    public PurHistoryListAdapter(List<PurchaseModel> purchaseModelList, HomeActivity homeActivity){
        this.purchaseModelList = purchaseModelList;
        this.homeActivity = homeActivity;
        this.purchaseListClickListeners = homeActivity;
    }

    @Override
    public int getItemCount() {
        return purchaseModelList.size() == 0 ? 1 :purchaseModelList.size() ;
    }

    @Override
    public int getItemViewType(int position) {
        if (purchaseModelList.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if(viewType == EMPTY_VIEW){
            final View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.empty_purchase_history_list, viewGroup, false);
            return new PurHistoryEmptyViewHolder(itemView);
        }
       final View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.adapt_purchase_history_list, viewGroup, false);




        return new PurHistoryListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof PurHistoryListViewHolder){
            PurHistoryListViewHolder purHistoryListViewHolder =(PurHistoryListViewHolder)viewHolder;

            final PurchaseModel purchaseModel =  purchaseModelList.get(position);
            purHistoryListViewHolder.vBillNumber.setText("Order Id: "+purchaseModel.getBillNumber());
            purHistoryListViewHolder.vName.setText("Shop: "+purchaseModel.getName()+","+purchaseModel.getArea());
            purHistoryListViewHolder.vPurchaseDateTime.setText(ServiceUtil.getDateTimeAsString(purchaseModel.getDateTime()));
            purHistoryListViewHolder.vTotalAmount.setText(homeActivity.getResources().getString(R.string.indian_rupee_symbol)+""+purchaseModel.getTotalAmount());
            if(purchaseModel.getOrderStatus().equals(OrderStatus.CANCELED.toString())){
                purHistoryListViewHolder.vStatus.setBackgroundResource(R.mipmap.cancel_32);
            }else{
                purHistoryListViewHolder.vStatus.setBackgroundResource(R.mipmap.delivered_img_32);
            }
            Picasso.with(homeActivity)
                    .load(ServiceAPI.INSTANCE.getUrl()+"/user/merchant/profilepic.html?merchantGuid="+purchaseModel.getMerchantGuid()+"&merchantId="+purchaseModel.getServerMerchantId())
                    .placeholder(ContextCompat.getDrawable(homeActivity,R.mipmap.luggage_cart))
                    .error(ContextCompat.getDrawable(homeActivity,R.mipmap.luggage_cart))
                    .into(purHistoryListViewHolder.vShopLogo);

        }


    }

    class PurHistoryEmptyViewHolder extends RecyclerView.ViewHolder{
        protected TextView vPurchaseDateTime;
        public PurHistoryEmptyViewHolder(View view){
            super(view);
            vPurchaseDateTime = (TextView) view.findViewById(R.id.empty_history_list_text);
        }
    }

    class PurHistoryListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vPurchaseDateTime;
        protected TextView vBillNumber;
        protected TextView vTotalAmount;
        protected TextView vName;
        protected ImageView vShopLogo;
        protected ImageView vStatus;



        public PurHistoryListViewHolder(View view){
            super(view);
            vPurchaseDateTime = (TextView) view.findViewById(R.id.adapt_pur_his_date);
            vBillNumber = (TextView) view.findViewById(R.id.adapt_pur_his_bill_no);
            vTotalAmount = (TextView) view.findViewById(R.id.adapt_pur_his_amount);
            vName = (TextView) view.findViewById(R.id.adapt_pur_his_shop_name);
            vShopLogo = (ImageView)view.findViewById(R.id.adapt_pur_his_shop_logo);
            vStatus = (ImageView)view.findViewById(R.id.adapt_pur_his_status);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PurchaseModel purchaseModel = purchaseModelList.get(getAdapterPosition());
                    int purchaseId = purchaseModel.getPurchaseId();
                    // itemView.setFocusable(true);
                    purchaseListClickListeners.purchaseListOnClick(purchaseId,3); //PurchaseDetailsActivity.PURCHASE_LIST
                }
            });

        }
    }

    public interface PurchaseListClickListeners{
        void purchaseListOnClick(int purchaseId,int fragmentOptions);
    }
}

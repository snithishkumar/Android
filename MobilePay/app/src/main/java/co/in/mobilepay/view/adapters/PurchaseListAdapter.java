package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.ImageLoader;
import co.in.mobilepay.view.activities.HomeActivity;
import co.in.mobilepay.view.model.PurchaseModel;

/**
 * Created by Nithish on 16-02-2016.
 */
public class PurchaseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PurchaseModel> purchaseModelList;

    private int position;
    PurchaseListClickListeners purchaseListClickListeners;
    private HomeActivity homeActivity;
    private ImageLoader imageLoader;

    private static final int EMPTY_VIEW = -1;

    public PurchaseListAdapter( List<PurchaseModel> purchaseModelList,HomeActivity homeActivity){
        this.purchaseModelList = purchaseModelList;
        this.homeActivity = homeActivity;
        this.purchaseListClickListeners = homeActivity;
        imageLoader = new ImageLoader(homeActivity);
    }

    @Override
    public int getItemCount() {
        return purchaseModelList.size() > 0 ? purchaseModelList.size() : 1;
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
                    inflate(R.layout.empty_purchase_list, viewGroup, false);
            return new EmptyViewHolder(itemView);
        }else{
            final View itemView = LayoutInflater.
                    from(viewGroup.getContext()).
                    inflate(R.layout.adapt_purchase_list, viewGroup, false);
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

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof PurchaseListViewHolder){
            PurchaseListViewHolder purchaseListViewHolder = (PurchaseListViewHolder)viewHolder;
            this.position = position;
            PurchaseModel purchaseModel =  purchaseModelList.get(position);
            // purchaseListViewHolder.vPurchaseDateTime.setText(ServiceUtil.getDateTimeAsString(purchaseModel.getDateTime()));
            purchaseListViewHolder.vShopName.setText(purchaseModel.getName());
            purchaseListViewHolder.vOrderId.setText(purchaseModel.getBillNumber());
            purchaseListViewHolder.vTotalAmount.setText(homeActivity.getResources().getString(R.string.indian_rupee_symbol)+""+purchaseModel.getTotalAmount());
            purchaseListViewHolder.vPurchaseDate.setText(ServiceUtil.getDateTimeAsString(purchaseModel.getDateTime()));
            imageLoader.displayImage(purchaseModel.getMerchantGuid(),purchaseModel.getServerMerchantId(),purchaseListViewHolder.vShopLogo);
        }

    }

    class PurchaseListViewHolder extends RecyclerView.ViewHolder{
        protected ImageView vShopLogo;
        protected TextView vShopName;
        protected TextView vOrderId;
        protected TextView vTotalAmount;
        protected TextView vPurchaseDate;


        public PurchaseListViewHolder(View view){
            super(view);
            vShopLogo = (ImageView) view.findViewById(R.id.adapt_pur_list_shop_logo);
            vShopName = (TextView) view.findViewById(R.id.adapt_pur_list_shop_name);
            vOrderId = (TextView) view.findViewById(R.id.adapt_pur_list_order_id);
            vTotalAmount = (TextView) view.findViewById(R.id.adapt_pur_list_total_amount);
            vPurchaseDate = (TextView) view.findViewById(R.id.adapt_pur_list_pur_date);
        }
    }


    class  EmptyViewHolder extends RecyclerView.ViewHolder{
        protected TextView vEmptyView;
        public EmptyViewHolder(View view){
            super(view);
            vEmptyView = (TextView) view.findViewById(R.id.empty_purchase_list_text);

        }
    }

    public interface PurchaseListClickListeners{
         void purchaseListOnClick(int purchaseId);
    }
}

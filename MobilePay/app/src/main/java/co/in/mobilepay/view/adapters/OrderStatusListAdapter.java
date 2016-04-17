package co.in.mobilepay.view.adapters;

import android.content.Intent;
import android.net.Uri;
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
public class OrderStatusListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PurchaseModel> purchaseModelList;

    private int position;
    PurchaseListClickListeners purchaseListClickListeners;
    private HomeActivity homeActivity;
    private ImageLoader imageLoader;

    private static final int EMPTY_VIEW = -1;

    public OrderStatusListAdapter(List<PurchaseModel> purchaseModelList, HomeActivity homeActivity){
        this.purchaseModelList = purchaseModelList;
        this.homeActivity = homeActivity;
        imageLoader = new ImageLoader(homeActivity);
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
                    inflate(R.layout.empty_order_status_list, viewGroup, false);
            return new OrderStatusEmptyViewHolder(itemView);
        }
       final View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.adapt_order_status_list, viewGroup, false);
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
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof LuggageListViewHolder){
            LuggageListViewHolder luggageListViewHolder = (LuggageListViewHolder)viewHolder;
            final PurchaseModel purchaseModel =  purchaseModelList.get(position);
            luggageListViewHolder.vBillNumber.setText("Order Id: "+purchaseModel.getBillNumber());
            luggageListViewHolder.vName.setText("Shop: "+purchaseModel.getName()+","+purchaseModel.getArea());
            luggageListViewHolder.vPurchaseDateTime.setText(ServiceUtil.getDateTimeAsString(purchaseModel.getDateTime()));
            luggageListViewHolder.vCategory.setText("Category: "+purchaseModel.getCategory());
            luggageListViewHolder.vTotalAmount.setText(homeActivity.getResources().getString(R.string.indian_rupee_symbol)+""+purchaseModel.getTotalAmount());
            luggageListViewHolder.vOrderStatus.setText("Order Status:"+purchaseModel.getOrderStatus());
            luggageListViewHolder.vCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + purchaseModel.getContactNumber()));
                    try {
                        homeActivity.startActivity(callIntent);  // TODO -- Need to handle request
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            imageLoader.displayImage(purchaseModel.getMerchantGuid(), purchaseModel.getServerMerchantId(), luggageListViewHolder.vShopLogo);
        }


    }

    class OrderStatusEmptyViewHolder extends RecyclerView.ViewHolder{
        protected TextView vEmptyMessage;
        public OrderStatusEmptyViewHolder(View view){
            super(view);
            vEmptyMessage = (TextView) view.findViewById(R.id.empty_order_status_list_text);
        }
    }

    class LuggageListViewHolder extends RecyclerView.ViewHolder{
        protected TextView vPurchaseDateTime;
        protected TextView vBillNumber;
        protected TextView vTotalAmount;
        protected TextView vName;
        protected TextView vCategory;
        protected TextView vCall;
        protected TextView vOrderStatus;
        protected ImageView vShopLogo;



        public LuggageListViewHolder(View view){
            super(view);
            vPurchaseDateTime = (TextView) view.findViewById(R.id.luggage_pur_date);
            vBillNumber = (TextView) view.findViewById(R.id.luggage_pur_bill_no);
            vTotalAmount = (TextView) view.findViewById(R.id.luggage_pur_total_amt);
            vName = (TextView) view.findViewById(R.id.luggage_pur_shop_name);
            vCategory = (TextView) view.findViewById(R.id.luggage_pur_shop_category);
            vCall = (TextView) view.findViewById(R.id.luggage_pur_call);
            vOrderStatus = (TextView) view.findViewById(R.id.luggage_pur_status);
            vShopLogo = (ImageView)view.findViewById(R.id.adapt_order_status_shop_logo);

        }
    }

    public interface PurchaseListClickListeners{
         void purchaseListOnClick(int purchaseId);
    }
}

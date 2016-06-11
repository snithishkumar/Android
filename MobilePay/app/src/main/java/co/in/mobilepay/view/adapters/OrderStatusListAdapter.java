package co.in.mobilepay.view.adapters;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.sync.ServiceAPI;
import co.in.mobilepay.view.activities.HomeActivity;
import co.in.mobilepay.view.model.PurchaseModel;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * Created by Nithish on 16-02-2016.
 */
public class OrderStatusListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PurchaseModel> purchaseModelList;

    PurchaseListClickListeners purchaseListClickListeners;
    private HomeActivity homeActivity;

    private static final int EMPTY_VIEW = -1;



    public OrderStatusListAdapter(List<PurchaseModel> purchaseModelList, HomeActivity homeActivity){
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


    private void showInfo1(View view,PurchaseModel purchaseModel){
        String msg = "Your order is ready. Please collect " +
                "your order in ground floor("+purchaseModel.getName()+"). Your counter id  is "+purchaseModel.getCounterNumber();
        new SimpleTooltip.Builder(homeActivity)
                .anchorView(view)
                .text(msg)
                .gravity(Gravity.TOP)
                .dismissOnOutsideTouch(true)
                .dismissOnInsideTouch(true)
                .build()
                .show();
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

            if(purchaseModel.getOrderStatus().equals("READY TO COLLECT")){
                luggageListViewHolder.vInfo.setVisibility(View.VISIBLE);
            }else{
                luggageListViewHolder.vOrderStatus.setOnClickListener(null);
            }
            Picasso.with(homeActivity)
                    .load(ServiceAPI.INSTANCE.getUrl()+"/user/merchant/profilepic.html?merchantGuid="+purchaseModel.getMerchantGuid()+"&merchantId="+purchaseModel.getServerMerchantId())
                    .placeholder(ContextCompat.getDrawable(homeActivity,R.mipmap.luggage_cart))
                    .error(ContextCompat.getDrawable(homeActivity,R.mipmap.luggage_cart))
                    .into(luggageListViewHolder.vShopLogo);

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
        protected ImageView vInfo;



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
            vInfo = (ImageView)view.findViewById(R.id.adapt_order_status_info);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PurchaseModel purchaseModel = purchaseModelList.get(getAdapterPosition());
                    int purchaseId = purchaseModel.getPurchaseId();
                    // itemView.setFocusable(true);
                    purchaseListClickListeners.purchaseListOnClick(purchaseId,2); ////PurchaseDetailsActivity.ORDER_STATUS_LIST
                }
            });

            vCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PurchaseModel purchaseModel = purchaseModelList.get(getAdapterPosition());
                    homeActivity.makeCall(purchaseModel.getContactNumber());
                   return;
                }
            });

            vInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PurchaseModel purchaseModel = purchaseModelList.get(getAdapterPosition());
                    showInfo1(v,purchaseModel);
                }
            });

            vOrderStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PurchaseModel purchaseModel = purchaseModelList.get(getAdapterPosition());
                    showInfo1(v,purchaseModel);
                }
            });

        }


    }

    public interface PurchaseListClickListeners{
        void purchaseListOnClick(int purchaseId,int fragmentOptions);
    }





}

package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.entity.DiscardEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.enumeration.DiscountType;
import co.in.mobilepay.enumeration.OrderStatus;
import co.in.mobilepay.json.response.CalculatedAmounts;
import co.in.mobilepay.util.MobilePayUtil;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.model.AmountDetailsJson;
import co.in.mobilepay.view.model.ProductDetailsModel;


public class ProductDetailsHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  List<ProductDetailsModel> productDetailsModels = new ArrayList<>();

    private PurchaseDetailsActivity purchaseDetailsActivity;
    private ProductDetailsModel productDetailsModel;

    private PurchaseEntity purchaseEntity;


    private AmountDetailsJson amountDetailsJson;

    private CalculatedAmounts calculatedAmounts;


    private static final int PRODUCT_DETAILS = 1;
    private static final int HOME_DELIVERY_OPTIONS = 2;
    private static final int TAX_DETAILS = 3;
    private static final int CANCEL = 4;




    public ProductDetailsHistoryAdapter(PurchaseDetailsActivity purchaseDetailsActivity, List<ProductDetailsModel> productDetailsModels, AmountDetailsJson amountDetailsJson, PurchaseEntity purchaseEntity,CalculatedAmounts calculatedAmounts) {
        this.productDetailsModels = productDetailsModels;
        this.purchaseDetailsActivity = purchaseDetailsActivity;
        this.amountDetailsJson =  amountDetailsJson;
        this.purchaseEntity = purchaseEntity;
        this.amountDetailsJson = amountDetailsJson;
        this.calculatedAmounts = calculatedAmounts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){

            case HOME_DELIVERY_OPTIONS:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_purchase_history_delivery, parent, false);
                return new DeliveryAddressViewHolder(view);

            case CANCEL:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_purchase_history_reason_cancel, parent, false);
                return new CancelViewHolder(view);

            case TAX_DETAILS:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_purchase_history_amount_details, parent, false);
                return new AmountDetailsViewHolder(view);

            default:
                 view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_purchase_history_items, parent, false);
                return new ProductDetailsViewHolder(view);
        }


    }
    @Override
    public int getItemCount() {
        return productDetailsModels.size()+2 ;
    }

    @Override
    public int getItemViewType(int position) {
        int size = productDetailsModels.size();
        if(size  == position){
            if(purchaseEntity.getOrderStatus().ordinal() == OrderStatus.CANCELLED.ordinal()){
                return CANCEL;
            }
            return HOME_DELIVERY_OPTIONS;
        }
        if(size + 1 == position){
            return TAX_DETAILS;
        }
        return PRODUCT_DETAILS;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof ProductDetailsViewHolder){
            ProductDetailsViewHolder productDetailsViewHolder = (ProductDetailsViewHolder)viewHolder;
            productDetailsModel = productDetailsModels.get(position);
            productDetailsViewHolder.ratingBar.setRating(productDetailsModel.getRating());
            toggleImg(productDetailsModel.getRating(),productDetailsViewHolder.rateItText);
            productDetailsViewHolder.name.setText(productDetailsModel.getDescription());

            productDetailsViewHolder.quantity.setText(String.valueOf(productDetailsModel.getQuantity()));

            productDetailsViewHolder.unitPrice.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,productDetailsModel.getUnitPrice()));

            productDetailsViewHolder.amount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,productDetailsModel.getAmount()));
        }else if(viewHolder instanceof  DeliveryAddressViewHolder){
            DeliveryAddressViewHolder deliveryAddressViewHolder = (DeliveryAddressViewHolder)viewHolder;
            switch (purchaseEntity.getUserDeliveryOptions()){
                case HOME:
                    if(purchaseEntity.getAddressEntity() != null){
                        String address  = purchaseEntity.getAddressEntity().getAddress();
                        deliveryAddressViewHolder.vHomeDelivery.setText(address);
                    }else{
                        deliveryAddressViewHolder.vHomeDelivery.setText(R.string.delivery_home);
                    }
                    break;
                case COUNTER_COLLECTION:
                    deliveryAddressViewHolder.vHomeDelivery.setText(R.string.delivery_collection_counter);
                    break;
                case NONE:
                    deliveryAddressViewHolder.vHomeDelivery.setText(R.string.delivery_none);
                    break;
            }


        }else if (viewHolder instanceof CancelViewHolder) {
            CancelViewHolder cancelViewHolder = (CancelViewHolder)viewHolder;
            DiscardEntity discardEntity = purchaseDetailsActivity.getPurchaseService().getDiscardEntity(purchaseEntity);
            cancelViewHolder.vCancelText.setText(discardEntity.getReason());

        } else {
            AmountDetailsViewHolder amountDetailsViewHolder = (AmountDetailsViewHolder) viewHolder;

            amountDetailsViewHolder.vSubTotalAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,calculatedAmounts.getPurchasedAmount()));
            amountDetailsViewHolder.vTaxText.setText("Tax (" + amountDetailsJson.getTaxAmount() + " % of total)");

            amountDetailsViewHolder.vSubTaxAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,calculatedAmounts.getTax()));
            if (amountDetailsJson.getDiscountType().getDiscountType() == DiscountType.AMOUNT.getDiscountType()) {
                amountDetailsViewHolder.vDiscountText.setText("Discount (" + amountDetailsJson.getDiscount() + " of total)");

            } else {
                amountDetailsViewHolder.vDiscountText.setText("Discount (" + amountDetailsJson.getDiscount() + " % of total)");
            }

            amountDetailsViewHolder.vSubDiscountAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,calculatedAmounts.getDiscount()));
            amountDetailsViewHolder.vTotalAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,calculatedAmounts.getTotalAmount()));
        }



    }



    private void toggleImg(float rating,TextView rateItText){
        if(rating > 0){
            if(rating < 2){
                rateItText.setText(purchaseDetailsActivity.getResources().getString(R.string.rate_bad));
            }else if(rating < 3){
                rateItText.setText(purchaseDetailsActivity.getResources().getString(R.string.rate_avg));
            }else if(rating < 4){
                rateItText.setText(purchaseDetailsActivity.getResources().getString(R.string.rate_ok));
            }else if(rating < 5){
                rateItText.setText(purchaseDetailsActivity.getResources().getString(R.string.rate_gud));
            }else{
                rateItText.setText(purchaseDetailsActivity.getResources().getString(R.string.rate_excellence));
            }
        }
    }


    public class ProductDetailsViewHolder extends RecyclerView.ViewHolder{

        private TextView quantity;
        private TextView unitPrice;
        private TextView name;
        private RatingBar ratingBar;
        private TextView amount;
        private TextView rateItText;

        public ProductDetailsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.adapt_pur_his_item_desc);
            ratingBar = (RatingBar) view.findViewById(R.id.adapt_pur_his_item_rate_it);
            rateItText = (TextView) view.findViewById(R.id.adapt_pur_his_item_rate_it_text);
            if(purchaseEntity.getOrderStatus().ordinal() == OrderStatus.CANCELLED.ordinal()){
                ratingBar.setVisibility(View.INVISIBLE);
                rateItText.setVisibility(View.INVISIBLE);

            }
            amount = (TextView) view.findViewById(R.id.adapt_pur_his_item_total_amount);
            unitPrice = (TextView) view.findViewById(R.id.adapt_pur_his_item_amount);
            quantity = (TextView) view.findViewById(R.id.adapt_pur_his_item_quantity);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }




    }


    public class DeliveryAddressViewHolder extends RecyclerView.ViewHolder{
        private RadioButton vHomeDelivery = null;
        public DeliveryAddressViewHolder(View view){
            super(view);
            vHomeDelivery = (RadioButton) view.findViewById(R.id.adapt_pur_his_item_delivery_value);



        }
    }




    public class AmountDetailsViewHolder extends RecyclerView.ViewHolder{
        private TextView vTaxText;
        private TextView vDiscountText;

        private TextView vSubTotalAmount;
        private TextView vSubTaxAmount;
        private TextView vSubDiscountAmount;
        private TextView vTotalAmount;

        public AmountDetailsViewHolder(View view){
            super(view);
            vTaxText = (TextView) view.findViewById(R.id.adapt_pur_history_reason_message);
            vDiscountText = (TextView) view.findViewById(R.id.amount_details_sub_total_discount);
            vSubTotalAmount = (TextView) view.findViewById(R.id.amount_details_sub_total_amount);
            vSubTaxAmount = (TextView) view.findViewById(R.id.amount_details_sub_tax_amount);
            vSubDiscountAmount = (TextView) view.findViewById(R.id.amount_details_sub_discount_amount);
            vTotalAmount = (TextView) view.findViewById(R.id.amount_details_total_amount);
        }
    }

    private class CancelViewHolder extends RecyclerView.ViewHolder{
        private TextView vCancelText;
        public CancelViewHolder(View view){
            super(view);
            vCancelText = (TextView)view.findViewById(R.id.adapt_pur_history_reason_message);
        }

    }


}

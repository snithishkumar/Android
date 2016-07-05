package co.in.mobilepay.view.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import co.in.mobilepay.util.MobilePayUtil;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.model.AmountDetailsJson;
import co.in.mobilepay.view.model.ProductDetailsModel;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class ProductDetailsHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  List<ProductDetailsModel> productDetailsModels = new ArrayList<>();

    private PurchaseDetailsActivity purchaseDetailsActivity;
    private ProductDetailsModel productDetailsModel;

    private PurchaseEntity purchaseEntity;


    private AmountDetailsJson amountDetailsJson;

    private double amount;
    private double taxAmount;
    private double discount = 0;
    private double totalAmount;



    private static final int PRODUCT_DETAILS = 1;
    private static final int HOME_DELIVERY_OPTIONS = 2;
    private static final int TAX_DETAILS = 3;
    private static final int CANCEL = 4;




    public ProductDetailsHistoryAdapter(PurchaseDetailsActivity purchaseDetailsActivity, List<ProductDetailsModel> productDetailsModels, AmountDetailsJson amountDetailsJson, PurchaseEntity purchaseEntity) {
        this.productDetailsModels = productDetailsModels;
        this.purchaseDetailsActivity = purchaseDetailsActivity;
        this.amountDetailsJson =  amountDetailsJson;
        this.purchaseEntity = purchaseEntity;
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
            if(purchaseEntity.isDiscard()){
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
// purHistoryListViewHolder.vTotalAmount.setText(homeActivity.getResources().getString(R.string.indian_rupee_symbol)+""+purchaseModel.getTotalAmount());
            productDetailsViewHolder.name.setText(productDetailsModel.getDescription());

            productDetailsViewHolder.totalAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,productDetailsModel.getAmount()));
            calcAmount(position);
        }else if(viewHolder instanceof  DeliveryAddressViewHolder){
            DeliveryAddressViewHolder deliveryAddressViewHolder = (DeliveryAddressViewHolder)viewHolder;
            switch ( purchaseEntity.getDeliveryOptions()){
                case HOME:
                    if(purchaseEntity.getAddressEntity() != null){
                        String address  = getAddress(purchaseEntity.getAddressEntity());
                        deliveryAddressViewHolder.vHomeDelivery.setText(address);
                        deliveryAddressViewHolder.vHomeDelivery.setChecked(true);
                        deliveryAddressViewHolder.vLuggage.setTextColor(ContextCompat.getColor(purchaseDetailsActivity,R.color.darkgray));
                        deliveryAddressViewHolder.vBilling.setTextColor(ContextCompat.getColor(purchaseDetailsActivity,R.color.darkgray));
                    }
                    break;
                case LUGGAGE:
                    deliveryAddressViewHolder.vLuggage.setChecked(true);
                    deliveryAddressViewHolder.vHomeDelivery.setTextColor(ContextCompat.getColor(purchaseDetailsActivity,R.color.darkgray));
                    deliveryAddressViewHolder.vBilling.setTextColor(ContextCompat.getColor(purchaseDetailsActivity,R.color.darkgray));
                    break;
                case NONE:
                    deliveryAddressViewHolder.vBilling.setChecked(true);
                    deliveryAddressViewHolder.vHomeDelivery.setTextColor(ContextCompat.getColor(purchaseDetailsActivity,R.color.darkgray));
                    deliveryAddressViewHolder.vLuggage.setTextColor(ContextCompat.getColor(purchaseDetailsActivity,R.color.darkgray));
                    break;
            }


        }else if (viewHolder instanceof CancelViewHolder) {
            CancelViewHolder cancelViewHolder = (CancelViewHolder)viewHolder;
            DiscardEntity discardEntity = purchaseDetailsActivity.getPurchaseService().getDiscardEntity(purchaseEntity);
            cancelViewHolder.vCancelText.setText(discardEntity.getReason());

        } else {
            AmountDetailsViewHolder amountDetailsViewHolder = (AmountDetailsViewHolder) viewHolder;
            calcAmount();

            amountDetailsViewHolder.vSubTotalAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,amount));
            amountDetailsViewHolder.vTaxText.setText("Tax (" + amountDetailsJson.getTaxAmount() + " % of total)");

            amountDetailsViewHolder.vSubTaxAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,taxAmount));
            if (amountDetailsJson.getDiscountType().getDiscountType() == DiscountType.AMOUNT.getDiscountType()) {
                amountDetailsViewHolder.vDiscountText.setText("Discount (" + amountDetailsJson.getDiscount() + " of total)");

            } else {
                amountDetailsViewHolder.vDiscountText.setText("Discount (" + amountDetailsJson.getDiscount() + " % of total)");
            }

            amountDetailsViewHolder.vSubDiscountAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,discount));
            amountDetailsViewHolder.vTotalAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,totalAmount));
        }



    }




    private String getAddress(AddressEntity addressEntity){


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(addressEntity.getStreet());
        if(addressEntity.getAddress() != null){
            stringBuilder.append(","+addressEntity.getAddress());
        }
        if(addressEntity.getArea() != null){
            stringBuilder.append(","+addressEntity.getArea());
        }
        if(addressEntity.getCity() != null){
            stringBuilder.append(","+addressEntity.getCity());
        }


        stringBuilder.append(" - " + addressEntity.getPostalCode());
        return  stringBuilder.toString();
    }

    private void calcAmount(int position){
        amount = amount +  Double.valueOf(productDetailsModels.get(position).getAmount());
    }

    /*private void calcAmount(){
        if(amountDetailsJson.getDiscount() >  -1 && amount > amountDetailsJson.getDiscountMiniVal()){
            if(amountDetailsJson.getDiscountType().getDiscountType() == DiscountType.AMOUNT.getDiscountType()){
                totalAmount =   amount - amountDetailsJson.getDiscount();
                discount = amountDetailsJson.getDiscount();
            }else{
                discount = Double.valueOf(String.format("%.2f", ((amount * amountDetailsJson.getDiscount()) / 100)));
                totalAmount = amount - discount;
            }
        }else{
            totalAmount = amount;
        }

        taxAmount =  Double.valueOf(String.format("%.2f", (( totalAmount *  amountDetailsJson.getTaxAmount())/100 )));
        totalAmount = totalAmount + taxAmount;
    }*/

    private void calcAmount(){
        double discountAmount = 0;
        double discountMinAmount = 0;

        if(amountDetailsJson.getDiscount() != null && !amountDetailsJson.getDiscount().trim().isEmpty()){
            discountAmount = Double.valueOf(amountDetailsJson.getDiscount());
        }

        if(amountDetailsJson.getDiscountMiniVal() != null && !amountDetailsJson.getDiscountMiniVal().trim().isEmpty()){
            discountMinAmount = Double.valueOf(amountDetailsJson.getDiscountMiniVal());
        }

        if(discountAmount >  0 && amount > discountMinAmount){
            if(amountDetailsJson.getDiscountType().getDiscountType() == DiscountType.AMOUNT.getDiscountType()){
                totalAmount =   amount - discountAmount;
                discount = discountAmount;
            }else if(amountDetailsJson.getDiscountType().getDiscountType() == DiscountType.PERCENTAGE.getDiscountType()){
                discount = Double.valueOf(String.format("%.2f", ((amount * discountAmount) / 100)));
                totalAmount = amount - discount;
            }
        }else{
            totalAmount = amount;
        }

        taxAmount =  Double.valueOf(String.format("%.2f", (( totalAmount *  amountDetailsJson.getTaxAmount())/100 )));
        totalAmount = totalAmount + taxAmount;
        totalAmount =  Double.valueOf(String.format("%.2f", totalAmount));
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


   /* private void toggleImg(int rating,ProductDetailsViewHolder productDetailsViewHolder){
        switch (rating){

            case 1:
                productDetailsViewHolder.rate1.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rateItText.setText(purchaseDetailsActivity.getResources().getString(R.string.rate_bad));
                break;
            case 2:
                productDetailsViewHolder.rate1.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rate2.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rateItText.setText(purchaseDetailsActivity.getResources().getString(R.string.rate_avg));
                break;
            case 3:
                productDetailsViewHolder.rate1.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rate2.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rate3.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rateItText.setText(purchaseDetailsActivity.getResources().getString(R.string.rate_ok));
                break;
            case 4:
                productDetailsViewHolder.rate1.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rate2.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rate3.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rate4.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rateItText.setText(purchaseDetailsActivity.getResources().getString(R.string.rate_gud));
                break;
            case 5:
                productDetailsViewHolder.rate1.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rate2.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rate3.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rate4.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rate5.setImageResource(R.mipmap.fav_icon);
                productDetailsViewHolder.rateItText.setText(purchaseDetailsActivity.getResources().getString(R.string.rate_excellence));
                break;
        }
    }
*/





    public class ProductDetailsViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private RatingBar ratingBar;
      /*  private ImageView rate2;
        private ImageView rate3;
        private ImageView rate4;
        private ImageView rate5;
        private ImageView delete;*/
        private TextView totalAmount;
        private TextView rateItText;

        public ProductDetailsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.adapt_pur_his_item_desc);
            ratingBar = (RatingBar) view.findViewById(R.id.adapt_pur_his_item_rate_it);
            rateItText = (TextView) view.findViewById(R.id.adapt_pur_his_item_rate_it_text);
            if(purchaseEntity.isDiscard()){
                ratingBar.setVisibility(View.INVISIBLE);
                rateItText.setVisibility(View.INVISIBLE);

            }
            totalAmount = (TextView) view.findViewById(R.id.adapt_pur_his_item_amount);


        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }




    }


    public class DeliveryAddressViewHolder extends RecyclerView.ViewHolder{
        private RadioButton vHomeDelivery = null;
        private RadioButton vLuggage = null;
        private RadioButton vBilling = null;
        public DeliveryAddressViewHolder(View view){
            super(view);
            vHomeDelivery = (RadioButton) view.findViewById(R.id.adapt_pur_order_status_item_delivery_addr);

            vLuggage = (RadioButton) view.findViewById(R.id.adapt_pur_order_status_item_delivery_luggage);
            vBilling = (RadioButton) view.findViewById(R.id.adapt_pur_order_status_item_delivery_billing);


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
///adapt_order_status_pur_item_amount
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

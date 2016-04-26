package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.enumeration.DiscountType;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.model.AmountDetailsJson;
import co.in.mobilepay.view.model.ProductDetailsModel;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class OrderStatusDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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




    public OrderStatusDetailsAdapter(PurchaseDetailsActivity purchaseDetailsActivity, List<ProductDetailsModel> productDetailsModels, AmountDetailsJson amountDetailsJson,PurchaseEntity  purchaseEntity) {
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
                        .inflate(R.layout.adapt_purchase_order_status_delivery, parent, false);
                return new DeliveryAddressViewHolder(view);

            case TAX_DETAILS:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_purchase_amount_details, parent, false);
                return new AmountDetailsViewHolder(view);
            default:
                 view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_order_status_purchase_items, parent, false);
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
            toggleImg(productDetailsModel.getRating(),productDetailsViewHolder);
            productDetailsViewHolder.name.setText(productDetailsModel.getDescription());
            calcAmount(position);
        }else if(viewHolder instanceof  DeliveryAddressViewHolder){
            DeliveryAddressViewHolder deliveryAddressViewHolder = (DeliveryAddressViewHolder)viewHolder;
           ;
            switch ( purchaseEntity.getDeliveryOptions()){
                case HOME:
                    if(purchaseEntity.getAddressEntity() != null){
                        String address  = getAddress(purchaseEntity.getAddressEntity());
                        deliveryAddressViewHolder.vHomeDelivery.setText(address);
                        deliveryAddressViewHolder.vHomeDelivery.setChecked(true);
                    }
                    break;
                case LUGGAGE:
                    deliveryAddressViewHolder.vLuggage.setChecked(true);
                    break;
                case NONE:
                    deliveryAddressViewHolder.vBilling.setChecked(true);
                    break;
            }


        }else{
            AmountDetailsViewHolder amountDetailsViewHolder = (AmountDetailsViewHolder)viewHolder;
            calcAmount();
            amountDetailsViewHolder.vSubTotalAmount.setText(purchaseDetailsActivity.getResources().getString(R.string.indian_rupee_symbol)+""+amount);
            amountDetailsViewHolder.vTaxText.setText("Tax (" + amountDetailsJson.getTaxAmount() + " % of total)");
            amountDetailsViewHolder.vSubTaxAmount.setText(purchaseDetailsActivity.getResources().getString(R.string.indian_rupee_symbol)+""+String.valueOf(taxAmount));
            if(amountDetailsJson.getDiscountType().getDiscountType() == DiscountType.AMOUNT.getDiscountType()){
                amountDetailsViewHolder.vDiscountText.setText("Discount (" + amountDetailsJson.getDiscount() + " of total)");

            }else{
                amountDetailsViewHolder.vDiscountText.setText("Discount (" + amountDetailsJson.getDiscount() + " % of total)");
            }
            amountDetailsViewHolder.vSubDiscountAmount.setText(purchaseDetailsActivity.getResources().getString(R.string.indian_rupee_symbol)+""+String.valueOf(discount));
            amountDetailsViewHolder.vTotalAmount.setText(purchaseDetailsActivity.getResources().getString(R.string.indian_rupee_symbol)+""+String.valueOf(totalAmount));
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

    private void calcAmount(){
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
    }


    private void toggleImg(int rating,ProductDetailsViewHolder productDetailsViewHolder){
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






    public class ProductDetailsViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView rate1;
        private ImageView rate2;
        private ImageView rate3;
        private ImageView rate4;
        private ImageView rate5;
        private TextView totalAmount;
        private TextView rateItText;
        private ImageView delete;

        public ProductDetailsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.adapt_order_status_pur_item_desc);
            rate1 = (ImageView) view.findViewById(R.id.adapt_order_status_pur_item_rate1);
            rate2 = (ImageView) view.findViewById(R.id.adapt_order_status_pur_item_rate2);
            rate3 = (ImageView) view.findViewById(R.id.adapt_order_status_pur_item_rate3);
            rate4 = (ImageView) view.findViewById(R.id.adapt_order_status_pur_item_rate4);
            rate5 = (ImageView) view.findViewById(R.id.adapt_order_status_pur_item_rate5);
            totalAmount = (TextView) view.findViewById(R.id.adapt_order_status_pur_item_amount);
            delete = (ImageView)view.findViewById(R.id.adapt_pur_item_delete);
            rateItText = (TextView) view.findViewById(R.id.adapt_order_status_pur_item_rate_it);


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
        private TextView vSubTotalText;
        private TextView vTaxText;
        private TextView vDiscountText;

        private TextView vSubTotalAmount;
        private TextView vSubTaxAmount;
        private TextView vSubDiscountAmount;
        private TextView vTotalAmount;

        public AmountDetailsViewHolder(View view){
            super(view);
            vSubTotalText = (TextView) view.findViewById(R.id.amount_details_sub_total_text);
            vTaxText = (TextView) view.findViewById(R.id.adapt_pur_history_reason_message);
            vDiscountText = (TextView) view.findViewById(R.id.amount_details_sub_total_discount);

            vSubTotalAmount = (TextView) view.findViewById(R.id.amount_details_sub_total_amount);
            vSubTaxAmount = (TextView) view.findViewById(R.id.amount_details_sub_tax_amount);
            vSubDiscountAmount = (TextView) view.findViewById(R.id.amount_details_sub_discount_amount);
            vTotalAmount = (TextView) view.findViewById(R.id.amount_details_total_amount);
        }
    }



}

package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.enumeration.DeliveryOptions;
import co.in.mobilepay.enumeration.DiscountType;
import co.in.mobilepay.util.MobilePayUtil;
import co.in.mobilepay.view.activities.ActivityUtil;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.model.AmountDetailsJson;
import co.in.mobilepay.view.model.ProductDetailsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class ProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  List<ProductDetailsModel> productDetailsModels = new ArrayList<>();

    private PurchaseDetailsActivity purchaseDetailsActivity;
    private ProductDetailsModel productDetailsModel;
    private ShowDeliveryAddress showDeliveryAddress;

    private AddressEntity defaultAddress = null;

    private AmountDetailsJson amountDetailsJson;
    private PurchaseEntity purchaseEntity;

    private double amount;
    private double taxAmount;
    private double discount = 0;
    private double totalAmount;
    private DeliveryOptions deliveryOptions;



    private static final int PRODUCT_DETAILS = 1;
    private static final int HOME_DELIVERY_OPTIONS = 2;
    private static final int TAX_DETAILS = 3;


    public static final int NEW_HOME = 1;
    public static final int HOME_LIST = 2;



    public ProductDetailsAdapter(PurchaseDetailsActivity purchaseDetailsActivity, List<ProductDetailsModel> productDetailsModels, AmountDetailsJson amountDetailsJson, PurchaseEntity purchaseEntity) {
        this.productDetailsModels = productDetailsModels;
        this.purchaseDetailsActivity = purchaseDetailsActivity;
        this.showDeliveryAddress = purchaseDetailsActivity;
        this.amountDetailsJson =  amountDetailsJson;
        this.purchaseEntity = purchaseEntity;
    }

    private void clearValue(){
        amount = 0;
        taxAmount = 0;
        discount = 0;
        totalAmount = 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){

            case HOME_DELIVERY_OPTIONS:
                defaultAddress = purchaseDetailsActivity.getPurchaseService().getDefaultAddress();
                if(defaultAddress == null){
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.adapt_purchase_delivery_options, parent, false);
                    return new DeliveryOptionsViewHolder(view);
                }else{
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.adapt_purchase_delivery_with_address, parent, false);
                    return new DeliveryAddressViewHolder(view);
                }

            case TAX_DETAILS:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_purchase_amount_details, parent, false);
                return new AmountDetailsViewHolder(view);
            default:
                 view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_purchase_items, parent, false);
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
            productDetailsViewHolder.name.setText(productDetailsModel.getDescription());


            productDetailsViewHolder.quantity.setText(String.valueOf(productDetailsModel.getQuantity()));
            productDetailsViewHolder.quantityDub.setText(String.valueOf(productDetailsModel.getQuantity()));
            productDetailsViewHolder.amount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,productDetailsModel.getAmount()));

            productDetailsViewHolder.totalAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,productDetailsModel.getTotalAmount()));
            if(!purchaseEntity.isEditable() || productDetailsModels.size() == 1){
                productDetailsViewHolder.delete.setVisibility(View.INVISIBLE);
            }
            productDetailsViewHolder.ratingBar.setRating(productDetailsModel.getRating());
            toggleImg(productDetailsModel.getRating(),productDetailsViewHolder.rateItText);
            calcAmount(position);
        }else if(viewHolder instanceof DeliveryOptionsViewHolder){
            DeliveryOptionsViewHolder deliveryOptionsViewHolder = (DeliveryOptionsViewHolder)viewHolder;
            if(purchaseEntity.isDeliverable()){
                deliveryOptionsViewHolder.vHomeDelivery.setVisibility(View.VISIBLE);
            }
        }else if(viewHolder instanceof  DeliveryAddressViewHolder){
            DeliveryAddressViewHolder deliveryAddressViewHolder = (DeliveryAddressViewHolder)viewHolder;
            String address  = getAddress(purchaseDetailsActivity.getPurchaseService().getDefaultAddress());
            deliveryAddressViewHolder.vHomeDelivery.setText(address);
        }else{
            AmountDetailsViewHolder amountDetailsViewHolder = (AmountDetailsViewHolder)viewHolder;
            calcAmount();

            amountDetailsViewHolder.vSubTotalAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,amount));
            amountDetailsViewHolder.vTaxText.setText("Tax (" + amountDetailsJson.getTaxAmount() + " % of total)");

            amountDetailsViewHolder.vSubTaxAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,taxAmount));
            if(amountDetailsJson.getDiscountType().getDiscountType() == DiscountType.AMOUNT.getDiscountType()){
                amountDetailsViewHolder.vDiscountText.setText("Discount (" + amountDetailsJson.getDiscount() + " of total)");

            }else{
                amountDetailsViewHolder.vDiscountText.setText("Discount (" + amountDetailsJson.getDiscount() + " % of total)");
            }

            amountDetailsViewHolder.vSubDiscountAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,discount));
            amountDetailsViewHolder.vTotalAmount.setText(MobilePayUtil.thousandSeparator(purchaseDetailsActivity,totalAmount));
        }



    }

    private void addRating(float rating,int adapterPosition){
        productDetailsModel =  productDetailsModels.get(adapterPosition);
        productDetailsModel.setRating(rating);
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
        amount = amount +  Double.valueOf(productDetailsModels.get(position).getTotalAmount());
    }

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



    public class ProductDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name;
        private TextView quantity;
        private TextView amount;
        private TextView quantityDub;
        private TextView totalAmount;
        private TextView rateItText;
        private ImageView delete;

        private ImageView add;
        private ImageView reduce;

        private RatingBar ratingBar;

        public ProductDetailsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.adapt_pur_item_desc);

            quantity = (TextView) view.findViewById(R.id.adapt_pur_item_quantity);
            amount = (TextView) view.findViewById(R.id.adapt_pur_item_amount);
            quantityDub = (TextView) view.findViewById(R.id.adapt_pur_item_quantity_dub);

            add = (ImageView)view.findViewById(R.id.adapt_pur_item_add);
            reduce = (ImageView)view.findViewById(R.id.adapt_pur_item_reduce);

            totalAmount = (TextView) view.findViewById(R.id.adapt_pur_item_total_amount);
            delete = (ImageView)view.findViewById(R.id.adapt_pur_item_delete);
            rateItText =  (TextView) view.findViewById(R.id.rate_it_text);
            ratingBar = (RatingBar) view.findViewById(R.id.rate_it_star);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    toggleImg(rating,rateItText);
                    addRating(rating,getAdapterPosition());
                }
            });

            delete.setOnClickListener(this);
            add.setOnClickListener(this);
            reduce.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }



        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.adapt_pur_item_add:
                    clearValue();
                    ProductDetailsModel productDetailsModel =  productDetailsModels.get(getAdapterPosition());
                    productDetailsModel.setQuantity(productDetailsModel.getQuantity() + 1);
                    notifyDataSetChanged();
                    break;


                case R.id.adapt_pur_item_reduce:
                    clearValue();
                    ProductDetailsModel productDetailsModelTemp =  productDetailsModels.get(getAdapterPosition());
                    productDetailsModelTemp.setQuantity(productDetailsModelTemp.getQuantity() - 1);
                    notifyDataSetChanged();
                    break;

                case R.id.adapt_pur_item_delete:
                    if(productDetailsModels.size() <= 1){
                        ActivityUtil.showDialog(purchaseDetailsActivity,"Message","Please Decline.");
                    }else {
                        clearValue();
                        productDetailsModels.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                    break;

            }
        }
    }


    public class DeliveryAddressViewHolder extends RecyclerView.ViewHolder{
        private RadioButton vHomeDelivery = null;
        private RadioButton vLuggage = null;
        private RadioButton vBilling = null;
        public DeliveryAddressViewHolder(View view){
            super(view);
            vHomeDelivery = (RadioButton) view.findViewById(R.id.adapt_pur_item_delivery_addr);
            vLuggage = (RadioButton) view.findViewById(R.id.adapt_pur_item_delivery_luggage);
            vBilling = (RadioButton) view.findViewById(R.id.adapt_pur_item_delivery_billing);

            RadioGroup radioGroup = (RadioGroup)  view.findViewById(R.id.adapt_pur_item_delivery_options);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.adapt_pur_item_delivery_addr:
                            deliveryOptions = DeliveryOptions.HOME;
                            break;
                        case R.id.adapt_pur_item_delivery_luggage:
                            deliveryOptions = DeliveryOptions.LUGGAGE;
                            break;
                        case R.id.adapt_pur_item_delivery_billing:
                            deliveryOptions = DeliveryOptions.BILLING;
                            break;

                    }
                }
            });
        }
    }


    public class DeliveryOptionsViewHolder extends RecyclerView.ViewHolder{
        private RadioButton vHomeDelivery = null;
        private RadioButton vLuggage = null;
        private RadioButton vBilling = null;
        public DeliveryOptionsViewHolder(View view){
            super(view);
            vHomeDelivery = (RadioButton) view.findViewById(R.id.adapt_pur_delivery_home);

            /******/
           RadioGroup radioGroup = (RadioGroup)  view.findViewById(R.id.adapt_pur_delivery);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.adapt_pur_delivery_home:
                            showDeliveryAddress.viewFragment(NEW_HOME);
                            return;
                        case R.id.adapt_pur_delivery_luggage:
                            deliveryOptions = DeliveryOptions.LUGGAGE;
                            break;
                        case R.id.adapt_pur_delivery_billing:
                            deliveryOptions = DeliveryOptions.BILLING;
                            break;

                    }
                }
            });
            /******/
            vLuggage = (RadioButton) view.findViewById(R.id.adapt_pur_delivery_luggage);
            vBilling = (RadioButton) view.findViewById(R.id.adapt_pur_delivery_billing);
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


    public interface ShowDeliveryAddress{
        void viewFragment(int options);
    }

    public DeliveryOptions getDeliveryOptions() {
        return deliveryOptions;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public AddressEntity getDefaultAddress() {
        return defaultAddress;
    }
}

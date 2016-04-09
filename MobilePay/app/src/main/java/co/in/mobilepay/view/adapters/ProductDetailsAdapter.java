package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.AddressEntity;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
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

    private int position;

    private static final int PRODUCT_DETAILS = 1;
    private static final int HOME_DELIVERY_OPTIONS = 2;
    private static final int TAX_DETAILS = 3;


    public static final int NEW_HOME = 1;
    public static final int HOME_LIST = 2;



    public ProductDetailsAdapter(PurchaseDetailsActivity purchaseDetailsActivity, List<ProductDetailsModel> productDetailsModels) {
        this.productDetailsModels = productDetailsModels;
        this.purchaseDetailsActivity = purchaseDetailsActivity;
        this.showDeliveryAddress = purchaseDetailsActivity;
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
    public int getItemViewType(int position) {
        int size = productDetailsModels.size();
        if(size - 2 == position){
            return HOME_DELIVERY_OPTIONS;
        }

        if(size - 1 == position){
            return TAX_DETAILS;
        }
        return PRODUCT_DETAILS;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        this.position = position;
        if(viewHolder instanceof ProductDetailsViewHolder){
            ProductDetailsViewHolder productDetailsViewHolder = (ProductDetailsViewHolder)viewHolder;
        }else if(viewHolder instanceof DeliveryOptionsViewHolder){
            DeliveryOptionsViewHolder deliveryOptionsViewHolder = (DeliveryOptionsViewHolder)viewHolder;
        }else if(viewHolder instanceof  DeliveryAddressViewHolder){
            DeliveryAddressViewHolder deliveryAddressViewHolder = (DeliveryAddressViewHolder)viewHolder;
            String address  = getAddress(purchaseDetailsActivity.getPurchaseService().getDefaultAddress());
            deliveryAddressViewHolder.vHomeDelivery.setText(address);
        }else{
            AmountDetailsViewHolder amountDetailsViewHolder = (AmountDetailsViewHolder)viewHolder;
        }

        productDetailsModel =  productDetailsModels.get(position);

    }

    private void addRating(int rating){

        productDetailsModel =  productDetailsModels.get(position);
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



    @Override
    public int getItemCount() {
        return productDetailsModels.size();
    }


    public class ProductDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name;
        private ImageView rate1;
        private ImageView rate2;
        private ImageView rate3;
        private ImageView rate4;
        private ImageView rate5;
        private TextView totalAmount;
        private ImageView delete;
        int imagePos = 0;

        public ProductDetailsViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.adapt_pur_item_desc);
            rate1 = (ImageView) view.findViewById(R.id.adapt_pur_item_rate1);
            rate2 = (ImageView) view.findViewById(R.id.adapt_pur_item_rate2);
            rate3 = (ImageView) view.findViewById(R.id.adapt_pur_item_rate3);
            rate4 = (ImageView) view.findViewById(R.id.adapt_pur_item_rate4);
            rate5 = (ImageView) view.findViewById(R.id.adapt_pur_item_rate5);
            totalAmount = (TextView) view.findViewById(R.id.adapt_pur_item_amount);
            delete = (ImageView)view.findViewById(R.id.adapt_pur_item_delete);
            rate1.setOnClickListener(this);
            rate2.setOnClickListener(this);
            rate3.setOnClickListener(this);
            rate4.setOnClickListener(this);
            rate5.setOnClickListener(this);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }

        private void toggleImg(int rating){
            switch (rating){
                case 0:
                    rate1.setImageResource(R.mipmap.rate_no_value);
                    rate2.setImageResource(R.mipmap.rate_no_value);
                    rate3.setImageResource(R.mipmap.rate_no_value);
                    rate4.setImageResource(R.mipmap.rate_no_value);
                    rate5.setImageResource(R.mipmap.rate_no_value);
                    break;
                case 1:
                    rate1.setImageResource(R.mipmap.fav_icon);
                    rate2.setImageResource(R.mipmap.rate_no_value);
                    rate3.setImageResource(R.mipmap.rate_no_value);
                    rate4.setImageResource(R.mipmap.rate_no_value);
                    rate5.setImageResource(R.mipmap.rate_no_value);
                    break;
                case 2:
                    rate1.setImageResource(R.mipmap.fav_icon);
                    rate2.setImageResource(R.mipmap.fav_icon);
                    rate3.setImageResource(R.mipmap.rate_no_value);
                    rate4.setImageResource(R.mipmap.rate_no_value);
                    rate5.setImageResource(R.mipmap.rate_no_value);
                    break;
                case 3:
                    rate1.setImageResource(R.mipmap.fav_icon);
                    rate2.setImageResource(R.mipmap.fav_icon);
                    rate3.setImageResource(R.mipmap.fav_icon);
                    rate4.setImageResource(R.mipmap.rate_no_value);
                    rate5.setImageResource(R.mipmap.rate_no_value);
                    break;
                case 4:
                    rate1.setImageResource(R.mipmap.fav_icon);
                    rate2.setImageResource(R.mipmap.fav_icon);
                    rate3.setImageResource(R.mipmap.fav_icon);
                    rate4.setImageResource(R.mipmap.fav_icon);
                    rate5.setImageResource(R.mipmap.rate_no_value);
                    break;
                case 5:
                    rate1.setImageResource(R.mipmap.fav_icon);
                    rate2.setImageResource(R.mipmap.fav_icon);
                    rate3.setImageResource(R.mipmap.fav_icon);
                    rate4.setImageResource(R.mipmap.fav_icon);
                    rate5.setImageResource(R.mipmap.fav_icon);
                    break;
            }
            addRating(rating);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.adapt_pur_item_rate1:
                    if( imagePos == 0){
                        toggleImg(1);

                        imagePos = 1;
                    }else{
                        toggleImg(0);
                        imagePos = 0;
                    }

                    break;
                case R.id.adapt_pur_item_rate2:
                    if( imagePos == 0){
                        toggleImg(2);
                        imagePos = 1;
                    }else{
                        toggleImg(1);
                        imagePos = 0;
                    }
                    break;
                case R.id.adapt_pur_item_rate3:
                    if( imagePos == 0){
                        toggleImg(3);
                        imagePos = 1;
                    }else{
                        toggleImg(2);
                        imagePos = 0;
                    }
                    break;
                case R.id.adapt_pur_item_rate4:
                    if( imagePos == 0){
                        toggleImg(4);
                        imagePos = 1;
                    }else{
                        toggleImg(3);
                        imagePos = 0;
                    }
                    break;
                case R.id.adapt_pur_item_rate5:
                    if( imagePos == 0){
                        toggleImg(5);
                        imagePos = 1;
                    }else{
                        toggleImg(4);
                        imagePos = 0;
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
        }
    }


    public class DeliveryOptionsViewHolder extends RecyclerView.ViewHolder{
        private RadioButton vHomeDelivery = null;
        private RadioButton vLuggage = null;
        private RadioButton vBilling = null;
        public DeliveryOptionsViewHolder(View view){
            super(view);
            vHomeDelivery = (RadioButton) view.findViewById(R.id.adapt_pur_delivery_home);
            vHomeDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeliveryAddress.viewFragment(NEW_HOME);
                    return;
                }
            });
            vLuggage = (RadioButton) view.findViewById(R.id.adapt_pur_delivery_luggage);
            vBilling = (RadioButton) view.findViewById(R.id.adapt_pur_delivery_billing);
        }
    }

    public class AmountDetailsViewHolder extends RecyclerView.ViewHolder{
        public AmountDetailsViewHolder(View view){
            super(view);
        }
    }


    public interface ShowDeliveryAddress{
        void viewFragment(int options);
    }

}

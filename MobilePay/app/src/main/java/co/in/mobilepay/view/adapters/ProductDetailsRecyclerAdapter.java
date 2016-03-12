package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.in.mobilepay.R;
import co.in.mobilepay.view.model.ProductDetailsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class ProductDetailsRecyclerAdapter extends RecyclerView.Adapter<ProductDetailsRecyclerAdapter.ProductDetailsViewHolder> {

    private  List<ProductDetailsModel> productDetailsModels = new ArrayList<>();

    public ProductDetailsRecyclerAdapter( List<ProductDetailsModel> productDetailsModels) {
        this.productDetailsModels = productDetailsModels;
    }

    @Override
    public ProductDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_details_item, parent, false);
        return new ProductDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductDetailsViewHolder productDetailsViewHolder, int position) {
        ProductDetailsModel productDetailsModel =  productDetailsModels.get(position);
        productDetailsViewHolder.serialNo.setText(productDetailsModel.getItemNo()+". ");
        productDetailsViewHolder.name.setText(productDetailsModel.getDescription());
        productDetailsViewHolder.quantity.setText("Quantity:"+productDetailsModel.getQuantity());
        productDetailsViewHolder.totalAmount.setText(productDetailsModel.getAmount());

    }

    @Override
    public int getItemCount() {
        return productDetailsModels.size();
    }


    public class ProductDetailsViewHolder extends RecyclerView.ViewHolder {

        private TextView serialNo;
        private TextView name;
        private TextView quantity;
        private TextView totalAmount;

        public ProductDetailsViewHolder(View view) {
            super(view);
            serialNo = (TextView) view.findViewById(R.id.pur_details_list_sno);
            name = (TextView) view.findViewById(R.id.pur_details_list_name);
            quantity = (TextView) view.findViewById(R.id.pur_details_list_quantity);
            totalAmount = (TextView) view.findViewById(R.id.pur_details_list_totalamt);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

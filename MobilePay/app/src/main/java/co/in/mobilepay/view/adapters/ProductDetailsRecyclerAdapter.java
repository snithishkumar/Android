package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.in.mobilepay.R;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.model.ProductDetailsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class ProductDetailsRecyclerAdapter extends RecyclerView.Adapter<ProductDetailsRecyclerAdapter.ProductDetailsViewHolder> {

    private  List<ProductDetailsModel> productDetailsModels = new ArrayList<>();
    private PurchaseDetailsActivity purchaseDetailsActivity;
    private ProductDetailsModel productDetailsModel;

    public ProductDetailsRecyclerAdapter(PurchaseDetailsActivity purchaseDetailsActivity, List<ProductDetailsModel> productDetailsModels) {
        this.productDetailsModels = productDetailsModels;
        this.purchaseDetailsActivity = purchaseDetailsActivity;
    }

    @Override
    public ProductDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapt_purchase_items, parent, false);
        return new ProductDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductDetailsViewHolder productDetailsViewHolder, int position) {
        productDetailsModel =  productDetailsModels.get(position);
        productDetailsViewHolder.serialNo.setText("0"+productDetailsModel.getItemNo());
        productDetailsViewHolder.countDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productDetailsModel.getQuantity() > 1){
                    productDetailsViewHolder.quantity.setText(productDetailsModel.getQuantity() - 1+"");
                    productDetailsModel.setQuantity(productDetailsModel.getQuantity() - 1);
                }

            }
        });
       // productDetailsViewHolder.name.setText(productDetailsModel.getDescription());
        productDetailsViewHolder.totalAmount.setText(purchaseDetailsActivity.getResources().getString(R.string.indian_rupee_symbol)+""+productDetailsModel.getAmount());

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
        private ImageView countDecrement;

        public ProductDetailsViewHolder(View view) {
            super(view);
            serialNo = (TextView) view.findViewById(R.id.adapt_pur_item_no);
            name = (TextView) view.findViewById(R.id.adapt_pur_item_desc);
            quantity = (TextView) view.findViewById(R.id.adapt_pur_item_count);
            totalAmount = (TextView) view.findViewById(R.id.adapt_pur_item_amt);
            countDecrement = (ImageView)view.findViewById(R.id.adapt_pur_item_count_dec);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

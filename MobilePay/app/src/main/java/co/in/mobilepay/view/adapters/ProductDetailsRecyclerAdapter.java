package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.in.mobilepay.R;
import co.in.mobilepay.view.PurchaseModel;
import co.in.mobilepay.view.fragments.ProductsDetailsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class ProductDetailsRecyclerAdapter extends RecyclerView.Adapter<ProductDetailsRecyclerAdapter.ViewHolder> {

    private final ProductsDetailsFragment.OnListFragmentInteractionListener mListener;
    private List<PurchaseModel> items = new ArrayList<>();

    public ProductDetailsRecyclerAdapter(List<PurchaseModel> items,ProductsDetailsFragment.OnListFragmentInteractionListener listener) {
        mListener = listener;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = items.get(position);
        /*holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);*/

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public PurchaseModel mItem;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.amt);
            mContentView = (TextView) view.findViewById(R.id.offer);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}

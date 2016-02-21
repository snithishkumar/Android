package co.in.mobilepay.view.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.in.mobilepay.R;
import co.in.mobilepay.view.PurchaseModel;
import co.in.mobilepay.view.activities.HomeActivity;

import java.util.List;


/**
 * {@link RecyclerView.Adapter} that can display a {@link PurchaseModel} and makes a call to the
 * specified {@link PurchaseItemsFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<PurchaseModel> mValues;
    private final PurchaseItemsFragment.OnListFragmentInteractionListener mListener;
    private HomeActivity homeActivity = null;
    private Context context = null;

    public MyItemRecyclerViewAdapter(Context context,List<PurchaseModel> items, PurchaseItemsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        homeActivity = (HomeActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(holder.mItem.getName());
        holder.mAreaView.setText(holder.mItem.getArea());
        holder.mMobileView.setText(holder.mItem.getContactNumber());
        holder.mBillNoView.setText(holder.mItem.getBillNumber());
        holder.mDateView.setText(holder.mItem.getDateTime());
        holder.mCategoryView.setText(holder.mItem.getCategory());
        holder.mNoOfCountView.setText("No of counts: "+holder.mItem.getNoOfItems());
        holder.mTotalCountView.setText("Amt: "+holder.mItem.getTotalAmount());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    homeActivity.showProductListFragment();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mAreaView;
        public final TextView mMobileView;
        public final TextView mBillNoView;
        public final TextView mDateView;
        public final TextView mCategoryView;
        public final TextView mNoOfCountView;
        public final TextView mTotalCountView;
        public PurchaseModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mAreaView = (TextView) view.findViewById(R.id.area);
            mMobileView = (TextView) view.findViewById(R.id.mobile_no);
            mBillNoView = (TextView) view.findViewById(R.id.bill_no);
            mDateView = (TextView) view.findViewById(R.id.date);
            mCategoryView = (TextView) view.findViewById(R.id.category);
            mNoOfCountView = (TextView) view.findViewById(R.id.no_of_count);
            mTotalCountView = (TextView) view.findViewById(R.id.total_count);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", mNameView=" + mNameView +
                    ", mAreaView=" + mAreaView +
                    ", mMobileView=" + mMobileView +
                    ", mBillNoView=" + mBillNoView +
                    ", mDateView=" + mDateView +
                    ", mCategoryView=" + mCategoryView +
                    ", mNoOfCountView=" + mNoOfCountView +
                    ", mTotalCountView=" + mTotalCountView +
                    ", mItem=" + mItem +
                    '}';
        }
    }
}

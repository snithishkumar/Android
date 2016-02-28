package co.in.mobilepay.view.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.view.PurchaseModel;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PurchaseModel} and makes a call to the
 * specified {@link }.
 * TODO: Replace the implementation with code for your data type.
 */
public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private List<PurchaseModel> mValues = new ArrayList<>();
    private final PaymentCardFragment.OnListFragmentInteractionListener mListener;
    private int selectedPos = -1;

    public PaymentAdapter(List<PurchaseModel> items, PaymentCardFragment.OnListFragmentInteractionListener listener) {
        this.mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_paymentcard, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(position == selectedPos){
            holder.mCvvEdit.setVisibility(View.VISIBLE);
            holder.floatingActionButton.setVisibility(View.VISIBLE);
            holder.mArrowImage.setImageResource(R.mipmap.arrow_up);
        }else {
            holder.mCvvEdit.setVisibility(View.GONE);
            holder.floatingActionButton.setVisibility(View.GONE);
            holder.mArrowImage.setImageResource(R.mipmap.arrow_down);
        }
        holder.mItem = mValues.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clicked = position;
                if(holder.mCvvEdit.isShown()){
                    clicked = -1;
                }
                clickedList(clicked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final EditText mCvvEdit;
        public final ImageView mArrowImage;
        public final FloatingActionButton floatingActionButton;
        public PurchaseModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCvvEdit = (EditText) view.findViewById(R.id.cvv);
            mArrowImage = (ImageView) view.findViewById(R.id.arrow);

            floatingActionButton = (FloatingActionButton) view.findViewById(R.id.submit);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }

    }
    private void clickedList(int position){
        selectedPos = position;
        notifyDataSetChanged();
    }
}

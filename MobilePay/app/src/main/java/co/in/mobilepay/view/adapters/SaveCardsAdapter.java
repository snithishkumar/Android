/*
package co.in.mobilepay.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.json.response.CardDetailsJson;
import co.in.mobilepay.json.response.CardJson;
import co.in.mobilepay.view.PurchaseModel;
import co.in.mobilepay.view.fragmentsw.SaveCardsFragment;

*/
/**
 * {@link RecyclerView.Adapter} that can display a {@link PurchaseModel} and makes a call to the
 * specified {@link }.
 * TODO: Replace the implementation with code for your data type.
 *//*

public class SaveCardsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<CardJson> cardJsonList = new ArrayList<>();
    SaveCardsFragment saveCardsFragment;

    private static final int EMPTY_VIEW = -1;

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(String cardGuid);
    }

    public SaveCardsAdapter(List<CardJson> cardJsonList,SaveCardsFragment saveCardsFragment) {
        this.cardJsonList = cardJsonList;
        this.saveCardsFragment = saveCardsFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == EMPTY_VIEW){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.empty_save_card_list, parent, false);
            return new EmptySaveCardViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapt_save_card_list, parent, false);
        return new SaveCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof SaveCardViewHolder){
            SaveCardViewHolder saveCardViewHolder = (SaveCardViewHolder)holder;
            final CardJson cardJson =  cardJsonList.get(position);
            CardDetailsJson cardDetailsJson = cardJson.getCardDetails();
            saveCardViewHolder.cardNumber.setText("xxxx-xxxx-xxxx-" + getLastFourDigits(cardDetailsJson.getNumber()));

            saveCardViewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    saveCardsFragment.onItemLongClicked(cardJson.getCardGuid());
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return cardJsonList.size() == 0 ? 1 :cardJsonList.size() ;
    }

    @Override
    public int getItemViewType(int position) {
        if (cardJsonList.size() == 0) {
            return EMPTY_VIEW;
        }
        return super.getItemViewType(position);
    }

    class EmptySaveCardViewHolder extends RecyclerView.ViewHolder{
        private TextView cardNumber;
        public EmptySaveCardViewHolder(View view) {
            super(view);
            cardNumber = (TextView)view.findViewById(R.id.empty_save_card_list_text);
        }
    }

    class SaveCardViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView cardNumber;
        private ImageView cardType;


        public SaveCardViewHolder(View view) {
            super(view);
            mView = view;
            cardNumber = (TextView)view.findViewById(R.id.pay_save_card_no);
            cardType =  (ImageView)view.findViewById(R.id.pay_save_card_type);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }

    }
    private void clickedList(int position){
       // selectedPos = position;
        notifyDataSetChanged();
    }

    private String getLastFourDigits(String cardNumber){
        int posAt = cardNumber.lastIndexOf("-");
        int totalSize= cardNumber.length();
        if(posAt < totalSize){
           return  cardNumber.substring(posAt+1,totalSize);
        }
        return "xxxx";
    }
}
*/

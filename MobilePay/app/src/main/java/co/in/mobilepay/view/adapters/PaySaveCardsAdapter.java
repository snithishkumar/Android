package co.in.mobilepay.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import co.in.mobilepay.json.response.CardDetailsJson;
import co.in.mobilepay.json.response.CardJson;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.fragments.PaymentFragment;
import co.in.mobilepay.view.fragments.SaveCardsFragment;

/**
 * Created by Nithish on 19-03-2016.
 */
public class PaySaveCardsAdapter extends RecyclerView.Adapter<PaySaveCardsAdapter.PaySaveCardViewHolder> {

    List<CardJson> cardJsonList = new ArrayList<>();
    PaymentFragment paymentFragment;
    private int selectedPos = -1;
    private Activity activity;

    public PaySaveCardsAdapter(Activity activity,List<CardJson> cardJsonList,PaymentFragment paymentFragment) {
        this.activity = activity;
        this.cardJsonList = cardJsonList;
        this.paymentFragment = paymentFragment;
    }

    @Override
    public PaySaveCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapt_pay_save_card_list, parent, false);
        return new PaySaveCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PaySaveCardViewHolder holder, final int position) {
        if(position  <= (cardJsonList.size() - 2)){
            final CardJson cardJson =  cardJsonList.get(position);
            CardDetailsJson cardDetailsJson = cardJson.getCardDetails();
            holder.cardNumber.setText("xxxx-xxxx-xxxx-" + getLastFourDigits(cardDetailsJson.getNumber()));
            holder.arrow.setImageResource(R.mipmap.arrow_down);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedPos == position){
                        holder.arrow.setImageResource(R.mipmap.arrow_down);
                        holder.cardCvv.setVisibility(View.GONE);
                        holder.fab.setVisibility(View.GONE);
                        selectedPos = -1;
                        cardJson.setIsExpanded(false);
                    }else{
                        holder.arrow.setImageResource(R.mipmap.arrow_up);
                        holder.cardCvv.setVisibility(View.VISIBLE);
                        holder.fab.setVisibility(View.VISIBLE);
                        selectedPos = position;
                        cardJson.setIsExpanded(true);
                    }
                    refreshListviewHeight();

                }
            });
        }else{
            holder.arrow.setVisibility(View.GONE);
            holder.cardNumber.setText("New Card");
            holder.cardNumber.setTextColor(Color.BLACK);
            holder.cardText.setText("Debit card / Credit Card");
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PurchaseDetailsActivity purchaseDetailsActivity =  (PurchaseDetailsActivity)activity;
                    purchaseDetailsActivity.payment();
                    return;


                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return cardJsonList.size();
    }


    public class PaySaveCardViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView cardNumber;
        private ImageView cardType;
        private ImageView arrow;
        private EditText cardCvv;
        private FloatingActionButton fab;
        private TextView cardText ;


        public PaySaveCardViewHolder(View view) {
            super(view);
            mView = view;
            cardNumber = (TextView)view.findViewById(R.id.pay_save_card_no);
            cardType =  (ImageView)view.findViewById(R.id.pay_save_card_type);
            arrow =  (ImageView)view.findViewById(R.id.pay_save_card_arrow);
            cardCvv =  (EditText)view.findViewById(R.id.pay_save_card_cvv);
            fab = (FloatingActionButton)view.findViewById(R.id.pay_save_card_submit);
            cardText = (TextView)view.findViewById(R.id.pay_save_card_text);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }

    }

    private String getLastFourDigits(String cardNumber){
        int posAt = cardNumber.lastIndexOf("-");
        int totalSize= cardNumber.length();
        if(posAt < totalSize){
            return  cardNumber.substring(posAt+1,totalSize);
        }
        return "xxxx";
    }

    public interface PaySaveCardsCallback{
        void  payment();
    }
    public void refreshListviewHeight(){
        int size = 80;
        for(CardJson cardJson : cardJsonList){
            if(cardJson.isExpanded()){
                size += 135;
            }else{
                size += 80;
            }
        }
        paymentFragment.refreshListviewHeight(size);
    }
}

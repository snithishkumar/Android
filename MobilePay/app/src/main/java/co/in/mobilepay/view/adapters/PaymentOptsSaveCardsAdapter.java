package co.in.mobilepay.view.adapters;

import android.app.Activity;
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
import co.in.mobilepay.view.fragments.PaymentOptionsFragment;

/**
 * Created by Nithish on 19-03-2016.
 */
public class PaymentOptsSaveCardsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<CardJson> cardJsonList = new ArrayList<>();
    PaymentOptionsFragment paymentOptionsFragment;
    private int selectedPos = -1;
    private Activity activity;

    private static final int CARD_DETAILS = 1;
    private static final int NEW_CARDS = 2;
    private static final int NET_BANKING = 3;



    public PaymentOptsSaveCardsAdapter(Activity activity, List<CardJson> cardJsonList, PaymentOptionsFragment paymentOptionsFragment) {
        this.activity = activity;
        this.cardJsonList = cardJsonList;
        this.paymentOptionsFragment = paymentOptionsFragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){

            case NEW_CARDS:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_pay_ops_cards, parent, false);
                return new NewCreditDebitCardViewHolder(view);
            case NET_BANKING:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_pay_ops_netbanking, parent, false);
                return new NetBankViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_pay_save_card_list, parent, false);
                return new PaySaveCardViewHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if(viewHolder instanceof  PaySaveCardViewHolder){
           final PaySaveCardViewHolder paySaveCardViewHolder = (PaySaveCardViewHolder)viewHolder;

            final CardJson cardJson =  cardJsonList.get(position);
            CardDetailsJson cardDetailsJson = cardJson.getCardDetails();
            paySaveCardViewHolder.cardNumber.setText("xxxx-xxxx-xxxx-" + getLastFourDigits(cardDetailsJson.getNumber()));
            paySaveCardViewHolder.arrow.setImageResource(R.mipmap.arrow_down);
            paySaveCardViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedPos == position){
                        paySaveCardViewHolder.arrow.setImageResource(R.mipmap.arrow_down);
                        paySaveCardViewHolder.cardCvv.setVisibility(View.GONE);
                        paySaveCardViewHolder.fab.setVisibility(View.GONE);
                        selectedPos = -1;
                        cardJson.setIsExpanded(false);
                    }else{
                        paySaveCardViewHolder.arrow.setImageResource(R.mipmap.arrow_up);
                        paySaveCardViewHolder.cardCvv.setVisibility(View.VISIBLE);
                        paySaveCardViewHolder.fab.setVisibility(View.VISIBLE);
                        selectedPos = position;
                        cardJson.setIsExpanded(true);
                    }
                    refreshListviewHeight();

                }
            });

        }else if(viewHolder instanceof  NewCreditDebitCardViewHolder){

        }else if(viewHolder instanceof  NetBankViewHolder){

        }
       /* if(position  <= (cardJsonList.size() - 2)){
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
        }*/



    }

    @Override
    public int getItemCount() {
        return cardJsonList.size() + 2;
    }


    @Override
    public int getItemViewType(int position) {
        int size = cardJsonList.size();
        if(size  == position){
            return NEW_CARDS;
        }
        if(size + 1 == position){
            return NET_BANKING;
        }
        return CARD_DETAILS;
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


    public class NewCreditDebitCardViewHolder extends RecyclerView.ViewHolder {
        public final View mView;


        public NewCreditDebitCardViewHolder(View view) {
            super(view);
            mView = view;

        }

        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }

    }


    public class NetBankViewHolder extends RecyclerView.ViewHolder {
        public final View mView;


        public NetBankViewHolder(View view) {
            super(view);
            mView = view;

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


    public void refreshListviewHeight(){
        int size = 80;
        for(CardJson cardJson : cardJsonList){
            if(cardJson.isExpanded()){
                size += 135;
            }else{
                size += 80;
            }
        }
        paymentOptionsFragment.refreshListviewHeight(size);
    }

  public interface PaySaveCardsCallback{
        void payment();
    }
}

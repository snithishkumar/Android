/*
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
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.json.response.CardDetailsJson;
import co.in.mobilepay.json.response.CardJson;
import co.in.mobilepay.view.activities.PurchaseDetailsActivity;
import co.in.mobilepay.view.fragments.PaymentOptionsFragment;

*/
/**
 * Created by Nithish on 19-03-2016.
 *//*

public class PaymentOptsSaveCardsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<CardJson> cardJsonList = new ArrayList<>();
    PaymentOptionsFragment paymentOptionsFragment;
    int purchaseId;
    private int selectedPos = -1;
    private PurchaseDetailsActivity purchaseDetailsActivity;

    private static final int CARD_DETAILS = 1;
    private static final int NEW_CARDS = 2;
    private static final int NET_BANKING = 3;
    private static final int AMOUNT_DETAILS = 4;



    public PaymentOptsSaveCardsAdapter(PurchaseDetailsActivity activity, List<CardJson> cardJsonList, PaymentOptionsFragment paymentOptionsFragment,int purchaseId) {
        this.purchaseDetailsActivity = activity;
        this.cardJsonList = cardJsonList;
        this.paymentOptionsFragment = paymentOptionsFragment;
        this.purchaseId = purchaseId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){

            case AMOUNT_DETAILS:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapt_pay_amount_details, parent, false);
                return new AmountDetailsViewHolder(view);

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

            final CardJson cardJson =  cardJsonList.get(position - 1);
            CardDetailsJson cardDetailsJson = cardJson.getCardDetails();
            paySaveCardViewHolder.cardNumber.setText("xxxx-xxxx-xxxx-" + getLastFourDigits(cardDetailsJson.getNumber()));
            paySaveCardViewHolder.arrow.setImageResource(R.mipmap.arrow_down);
           */
/* paySaveCardViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    refreshListviewHeight();

                }
            });*//*


        }else if(viewHolder instanceof  NewCreditDebitCardViewHolder){

        }else if(viewHolder instanceof  NetBankViewHolder){

        }else if(viewHolder instanceof AmountDetailsViewHolder){
            PurchaseEntity purchaseEntity =  purchaseDetailsActivity.getPurchaseService().getPurchaseDetails(purchaseId);
            AmountDetailsViewHolder amountDetailsViewHolder = (AmountDetailsViewHolder)viewHolder;
            amountDetailsViewHolder.vShopName.setText("for "+purchaseEntity.getMerchantEntity().getMerchantName());
            amountDetailsViewHolder.vTotalAmount.setText(purchaseDetailsActivity.getResources().getString(R.string.indian_rupee_symbol)+""+purchaseEntity.getTotalAmount());
        }

    }

    @Override
    public int getItemCount() {
        return cardJsonList.size() + 3;
    }


    @Override
    public int getItemViewType(int position) {
        int size = cardJsonList.size();
        if(position == 0){
            return AMOUNT_DETAILS;
        }
        if(position  == size + 1){
            return NEW_CARDS;
        }
        if(position == size + 2) {
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
            arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveCardsOnClick(getAdapterPosition());
                }
            });

        }


        private void saveCardsOnClick(int position){
            if(selectedPos == position){
                arrow.setImageResource(R.mipmap.arrow_down);
                cardCvv.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                selectedPos = -1;
               // cardJsonList.get(position - 1).setIsExpanded(false);
            }else{
                arrow.setImageResource(R.mipmap.arrow_up);
                cardCvv.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                selectedPos = position;
                //cardJsonList.get(position - 1).setIsExpanded(true);
            }
           // notifyDataSetChanged();// -- TODO Need to check any alternate
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

    public class AmountDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView vTotalAmount;
        private TextView vShopName;

        public AmountDetailsViewHolder(View view) {
            super(view);
            vTotalAmount = (TextView)view.findViewById(R.id.pay_total_amt);
            vShopName = (TextView)view.findViewById(R.id.pay_shop_name);

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
*/

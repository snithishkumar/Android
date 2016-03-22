package co.in.mobilepay.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import co.in.mobilepay.R;
import co.in.mobilepay.service.CardService;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.service.impl.CardServiceImpl;
import co.in.mobilepay.service.impl.PurchaseServiceImpl;
import co.in.mobilepay.view.adapters.PaySaveCardsAdapter;
import co.in.mobilepay.view.fragments.FragmentsUtil;
import co.in.mobilepay.view.fragments.NewCardFragment;
import co.in.mobilepay.view.fragments.PaymentFragment;
import co.in.mobilepay.view.fragments.ProductsDetailsFabFragment;
import co.in.mobilepay.view.fragments.ProductsDetailsFragment;
import co.in.mobilepay.view.fragments.ShopDetailsFragment;

/**
 * Created by Nithish on 09-03-2016.
 */
public class PurchaseDetailsActivity extends AppCompatActivity implements PaySaveCardsAdapter.PaySaveCardsCallback{

    ProductsDetailsFragment productsDetailsFragment = null;
    PurchaseService purchaseService = null;
    CardService cardService = null;

    private int purchaseId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_pur_details);
        showFragment();
    }

    /**
     * Initialize service layer
     */
    private void init(){
        try{
            purchaseId = getIntent().getIntExtra("purchaseId",0);
            purchaseService = new PurchaseServiceImpl(this);
            cardService = new CardServiceImpl(this);
        }catch (Exception e){
            Log.e("Error", "Error in init", e);
        }

    }

    private void showFragment(){
        productsDetailsFragment = new ProductsDetailsFragment();
        Bundle purchaseIdArgs = new Bundle();
        purchaseIdArgs.putInt("purchaseId",purchaseId);
        productsDetailsFragment.setArguments(purchaseIdArgs);
        FragmentsUtil.addFragment(this, productsDetailsFragment, R.id.pur_details_main_container);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public PurchaseService getPurchaseService() {
        return purchaseService;
    }

    public CardService getCardService() {
        return cardService;
    }

    /**
     * view shop details
     * @param view
     */
    public void viewShopDetails(View view){
        ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
        Bundle purchaseIdArgs = new Bundle();
        purchaseIdArgs.putInt("purchaseId",purchaseId);
        shopDetailsFragment.setArguments(purchaseIdArgs);
        FragmentsUtil.replaceFragment(this,shopDetailsFragment,R.id.pur_details_main_container);
    }

    /**
     * Show FAB buttons
     * @param view
     */
    public void showFabIcon(View view){
        ProductsDetailsFabFragment productsDetailsFabFragment = new ProductsDetailsFabFragment();
        FragmentsUtil.addFragment(this, productsDetailsFabFragment, R.id.pur_details_fab_container);
    }

    public void showPaymentFragment(View view){
        FragmentsUtil.removeFragment(this, R.id.pur_details_fab_container);
        PaymentFragment paymentFragment = new PaymentFragment();
        Bundle purchaseIdArgs = new Bundle();
        purchaseIdArgs.putInt("purchaseId",purchaseId);
        paymentFragment.setArguments(purchaseIdArgs);
        FragmentsUtil.replaceFragment(this, paymentFragment, R.id.pur_details_main_container);
    }

    @Override
    public void payment() {
        NewCardFragment newCardFragment = new NewCardFragment();
        Bundle purchaseIdArgs = new Bundle();
        purchaseIdArgs.putInt("purchaseId",purchaseId);
        newCardFragment.setArguments(purchaseIdArgs);
        FragmentsUtil.replaceFragment(this, newCardFragment, R.id.pur_details_main_container);
    }
}

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
import co.in.mobilepay.view.adapters.ProductDetailsAdapter;
import co.in.mobilepay.view.fragments.AddDeliveryAddressFragment;
import co.in.mobilepay.view.fragments.DeliveryAddressFragment;
import co.in.mobilepay.view.fragments.FragmentsUtil;
import co.in.mobilepay.view.fragments.NewCardFragment;
import co.in.mobilepay.view.fragments.OrderStatusProductDetailsFragment;
import co.in.mobilepay.view.fragments.PaymentFragment;
import co.in.mobilepay.view.fragments.ProductsDetailsFabFragment;
import co.in.mobilepay.view.fragments.ProductsDetailsFragment;
import co.in.mobilepay.view.fragments.ShopDetailsFragment;

/**
 * Created by Nithish on 09-03-2016.
 */
public class PurchaseDetailsActivity extends AppCompatActivity implements PaySaveCardsAdapter.PaySaveCardsCallback,ProductDetailsAdapter.ShowDeliveryAddress,AddDeliveryAddressFragment.ShowDeliveryAddress{

    // Fragments
    ProductsDetailsFragment productsDetailsFragment = null;
    OrderStatusProductDetailsFragment orderStatusProductDetailsFragment = null;

    // service
    PurchaseService purchaseService = null;
    CardService cardService = null;

    private static final int PURCHASE_LIST = 1;
    private static final int ORDER_STATUS_LIST = 2;
    private static final int PURCHASE_HISTORY_LIST = 3;

    private int fragmentOptions = 0;
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
            fragmentOptions = getIntent().getIntExtra("fragmentOptions",0);
            purchaseService = new PurchaseServiceImpl(this);
            cardService = new CardServiceImpl(this);
        }catch (Exception e){
            Log.e("Error", "Error in init", e);
        }

    }

    private void showFragment(){
        switch (fragmentOptions){
            case PURCHASE_LIST:
                productsDetailsFragment = new ProductsDetailsFragment();
                Bundle purchaseIdArgs = new Bundle();
                purchaseIdArgs.putInt("purchaseId",purchaseId);
                productsDetailsFragment.setArguments(purchaseIdArgs);
                FragmentsUtil.addFragment(this, productsDetailsFragment, R.id.pur_details_main_container);
                break;
            case ORDER_STATUS_LIST:
                orderStatusProductDetailsFragment = new OrderStatusProductDetailsFragment();
                purchaseIdArgs = new Bundle();
                purchaseIdArgs.putInt("purchaseId",purchaseId);
                orderStatusProductDetailsFragment.setArguments(purchaseIdArgs);
                FragmentsUtil.addFragment(this, orderStatusProductDetailsFragment, R.id.pur_details_main_container);
                break;
        }

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



    public void onClick(View view){
        switch (view.getId()){
            case R.id.shop_details_layout:
                ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
                Bundle purchaseIdArgs = new Bundle();
                purchaseIdArgs.putInt("purchaseId",purchaseId);
                shopDetailsFragment.setArguments(purchaseIdArgs);
                FragmentsUtil.replaceFragment(this,shopDetailsFragment,R.id.pur_details_main_container);
                break;
            case  R.id.pur_details_fab_container:
                FragmentsUtil.removeFragment(this, R.id.pur_details_fab_container);
                PaymentFragment paymentFragment = new PaymentFragment();
                purchaseIdArgs = new Bundle();
                purchaseIdArgs.putInt("purchaseId",purchaseId);
                paymentFragment.setArguments(purchaseIdArgs);
                FragmentsUtil.replaceFragment(this, paymentFragment, R.id.pur_details_main_container);
                break;
            case R.id.adapt_pur_item_delivery_addr_change:
                DeliveryAddressFragment deliveryAddressFragment = new DeliveryAddressFragment();
                FragmentsUtil.replaceFragment(this,deliveryAddressFragment,R.id.pur_details_main_container);
                break;
            case R.id.add_address:
                AddDeliveryAddressFragment addDeliveryAddressFragment = new AddDeliveryAddressFragment();
                FragmentsUtil.replaceFragment(this, addDeliveryAddressFragment, R.id.pur_details_main_container);
                break;
        }
    }




    @Override
    public void payment() {
        NewCardFragment newCardFragment = new NewCardFragment();
        Bundle purchaseIdArgs = new Bundle();
        purchaseIdArgs.putInt("purchaseId",purchaseId);
        newCardFragment.setArguments(purchaseIdArgs);
        FragmentsUtil.replaceFragment(this, newCardFragment, R.id.pur_details_main_container);
    }

    @Override
    public void viewFragment(int options) {
        if(options == ProductDetailsAdapter.NEW_HOME){
            AddDeliveryAddressFragment addDeliveryAddressFragment = new AddDeliveryAddressFragment();
            FragmentsUtil.replaceFragment(this, addDeliveryAddressFragment, R.id.pur_details_main_container);
        }else if(options == 3){
            FragmentsUtil.backPressed(this);
        }
    }




}

package co.in.mobilepay.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import co.in.mobilepay.R;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.service.impl.PurchaseServiceImpl;
import co.in.mobilepay.view.fragments.FragmentsUtil;
import co.in.mobilepay.view.fragments.ProductsDetailsFragment;
import co.in.mobilepay.view.fragments.ShopDetailsFragment;

/**
 * Created by Nithish on 09-03-2016.
 */
public class PurchaseDetailsActivity extends AppCompatActivity {

    ProductsDetailsFragment productsDetailsFragment = null;
    PurchaseService purchaseService = null;

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

    /**
     * view shop details
     * @param view
     */
    public void viewShopDetails(View view){
        ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
        FragmentsUtil.replaceFragment(this,shopDetailsFragment,R.id.pur_details_main_container);
    }
}

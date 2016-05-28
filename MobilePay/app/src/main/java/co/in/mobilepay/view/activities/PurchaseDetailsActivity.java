package co.in.mobilepay.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.razorpay.Checkout;

import org.json.JSONObject;

import co.in.mobilepay.R;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.entity.TransactionalDetailsEntity;
import co.in.mobilepay.enumeration.DeviceType;
import co.in.mobilepay.enumeration.OrderStatus;
import co.in.mobilepay.enumeration.PaymentStatus;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.service.ServiceUtil;
import co.in.mobilepay.service.impl.PurchaseServiceImpl;
import co.in.mobilepay.view.adapters.PaymentOptsSaveCardsAdapter;
import co.in.mobilepay.view.adapters.ProductDetailsAdapter;
import co.in.mobilepay.view.fragments.AddDeliveryAddressFragment;
import co.in.mobilepay.view.fragments.DeliveryAddressFragment;
import co.in.mobilepay.view.fragments.FragmentsUtil;
import co.in.mobilepay.view.fragments.NewCardFragment;
import co.in.mobilepay.view.fragments.OrderStatusProductDetailsFragment;
import co.in.mobilepay.view.fragments.PaymentOptionsFragment;
import co.in.mobilepay.view.fragments.ProductHistoryDetailsFragment;
import co.in.mobilepay.view.fragments.ProductsDetailsFragment;
import co.in.mobilepay.view.fragments.ShopDetailsFragment;

/**
 * Created by Nithish on 09-03-2016.
 */
public class PurchaseDetailsActivity extends AppCompatActivity implements
        PaymentOptsSaveCardsAdapter.PaySaveCardsCallback,ProductsDetailsFragment.ShowPaymentOptions,
        ProductDetailsAdapter.ShowDeliveryAddress,AddDeliveryAddressFragment.ShowDeliveryAddress{

    // Fragments
    ProductsDetailsFragment productsDetailsFragment = null;
    OrderStatusProductDetailsFragment orderStatusProductDetailsFragment = null;

    // service
    PurchaseService purchaseService = null;

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
        }catch (Exception e){
            Log.e("Error", "Error in init", e);
        }

    }

    /**
     * It is used to decide to show Purchase Details or Order Status or History
     */
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
            case PURCHASE_HISTORY_LIST:
               ProductHistoryDetailsFragment productHistoryDetailsFragment = new ProductHistoryDetailsFragment();
                purchaseIdArgs = new Bundle();
                purchaseIdArgs.putInt("purchaseId",purchaseId);
                productHistoryDetailsFragment.setArguments(purchaseIdArgs);
                FragmentsUtil.addFragment(this, productHistoryDetailsFragment, R.id.pur_details_main_container);
                break;
        }

    }


    /**
     * Click Event
     * @param view
     */
    public void onClick(View view){
        switch (view.getId()){
            case R.id.shop_details_layout:
            case R.id.purchase_history_shop_details_layout:
                ShopDetailsFragment shopDetailsFragment = new ShopDetailsFragment();
                Bundle purchaseIdArgs = new Bundle();
                purchaseIdArgs.putInt("purchaseId",purchaseId);
                shopDetailsFragment.setArguments(purchaseIdArgs);
                FragmentsUtil.replaceFragment(this,shopDetailsFragment,R.id.pur_details_main_container);
                break;
            /*case  R.id.pur_details_fab_container:
                FragmentsUtil.removeFragment(this, R.id.pur_details_fab_container);
                PaymentOptionsFragment paymentOptionsFragment = new PaymentOptionsFragment();
                purchaseIdArgs = new Bundle();
                purchaseIdArgs.putInt("purchaseId",purchaseId);
                paymentOptionsFragment.setArguments(purchaseIdArgs);
                FragmentsUtil.replaceFragment(this, paymentOptionsFragment, R.id.pur_details_main_container);
                break;*/
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


    /**
     * Call back function
     * @param options
     */
    @Override
    public void viewFragment(int options) {
        switch (options){
            case ProductDetailsAdapter.NEW_HOME:
                AddDeliveryAddressFragment addDeliveryAddressFragment = new AddDeliveryAddressFragment();
                FragmentsUtil.replaceFragment(this, addDeliveryAddressFragment, R.id.pur_details_main_container);
                break;
            case 3:
                FragmentsUtil.backPressed(this);
                break;
            case 4:
                PurchaseEntity purchaseEntity = purchaseService.getPurchaseDetails(purchaseId);
                startPayment(purchaseEntity);
                break;
              /*  PaymentOptionsFragment paymentOptionsFragment = new PaymentOptionsFragment();
                Bundle purchaseIdArgs = new Bundle();
                purchaseIdArgs.putInt("purchaseId",purchaseId);
                paymentOptionsFragment.setArguments(purchaseIdArgs);
                FragmentsUtil.replaceFragment(this, paymentOptionsFragment, R.id.pur_details_main_container);
                break;*/
        }

    }


   private void startPayment(PurchaseEntity purchaseEntity){
       try {
           String myKey = "rzp_test_sXXkgq2zryxkgg";

           Checkout razorpayCheckout = new Checkout();
           razorpayCheckout.setPublicKey(myKey);

           JSONObject options  = new JSONObject();
           //"{description:'Test Purchase',currency:'INR'}"
           options.put("currency","INR");
           options.put("amount", Double.valueOf(purchaseEntity.getTotalAmount())*100);
           options.put("name", purchaseEntity.getMerchantEntity().getMerchantName());
           JSONObject prefill = new JSONObject();
           prefill.put("contact",purchaseEntity.getUserEntity().getMobileNumber());
           prefill.put("name",purchaseEntity.getUserEntity().getName());
           options.put("prefill", prefill);
           razorpayCheckout.open(this, options);
       }catch (Exception e){
           e.printStackTrace();
       }


   }

    /**
            * The name of the function has to be
    *   onPaymentSuccess
    * Wrap your code in try catch, as shown, to ensure that this method runs correctly
    */
    public void onPaymentSuccess(String razorpayPaymentID){
        try {
            TransactionalDetailsEntity transactionalDetailsEntity =  getTransactionalDetailsEntity();
            transactionalDetailsEntity.setTransactionUUID(razorpayPaymentID);
            transactionalDetailsEntity.setPaymentStatus(PaymentStatus.SUCCESS);
            purchaseService.createTransactionDetails(transactionalDetailsEntity);

            PurchaseEntity purchaseEntity = transactionalDetailsEntity.getPurchaseEntity();
            purchaseEntity.setPaymentStatus(PaymentStatus.PAIED);
            purchaseEntity.setIsSync(false);
            purchaseEntity.setOrderStatus(OrderStatus.PACKING.toString());
            purchaseEntity.setLastModifiedDateTime(ServiceUtil.getCurrentTimeMilli());
            purchaseService.updatePurchaseEntity(purchaseEntity);

            Toast.makeText(this, "Payment Successfully made.", Toast.LENGTH_SHORT).show();
            finish();
        }
        catch (Exception e){
            Log.e("com.merchant", e.getMessage(), e);
        }
    }

    /**
     * The name of the function has to be
     *   onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    public void onPaymentError(int code, String response) {
        try {
            TransactionalDetailsEntity transactionalDetailsEntity =  getTransactionalDetailsEntity();
            transactionalDetailsEntity.setReason(response);
            transactionalDetailsEntity.setPaymentStatus(PaymentStatus.FAILURE);
            purchaseService.createTransactionDetails(transactionalDetailsEntity);
            Toast.makeText(this, "Payment failed: " + Integer.toString(code) + " " + response, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e("com.merchant", e.getMessage(), e);
        }
    }



    private TransactionalDetailsEntity getTransactionalDetailsEntity(){
        TransactionalDetailsEntity transactionalDetailsEntity = new TransactionalDetailsEntity();
        PurchaseEntity purchaseEntity = purchaseService.getPurchaseDetails(purchaseId);
        transactionalDetailsEntity.setAmount(Double.valueOf(purchaseEntity.getTotalAmount()));
        transactionalDetailsEntity.setDeviceType(DeviceType.Android);
        transactionalDetailsEntity.setPaymentDate(ServiceUtil.getCurrentTimeMilli());
        transactionalDetailsEntity.setPurchaseEntity(purchaseEntity);
        transactionalDetailsEntity.setImeiNumber(ServiceUtil.getIMEINumber(this));
        transactionalDetailsEntity.setTransactionUUID(ServiceUtil.generateUUID());
        transactionalDetailsEntity.setSync(false);
return transactionalDetailsEntity;
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
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public PurchaseService getPurchaseService() {
        return purchaseService;
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package co.in.mobilepay.view.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.application.MobilePayAnalytics;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.service.impl.PurchaseServiceImpl;
import co.in.mobilepay.view.adapters.OrderStatusListAdapter;
import co.in.mobilepay.view.adapters.PurHistoryListAdapter;
import co.in.mobilepay.view.adapters.PurchaseListAdapter;
import co.in.mobilepay.view.fragments.FragmentDrawer;
import co.in.mobilepay.view.fragments.OrderStatusListFragment;
import co.in.mobilepay.view.fragments.PurHistoryListFragment;
import co.in.mobilepay.view.fragments.PurchaseListFragment;

public class HomeActivity extends AppCompatActivity implements PurchaseListAdapter.PurchaseListClickListeners,OrderStatusListAdapter.PurchaseListClickListeners,PurHistoryListAdapter.PurchaseListClickListeners,FragmentDrawer.FragmentDrawerListener{

    private PurchaseService purchaseService;

    private int tabPosition;

    private String mobileNumber;

    boolean doubleBackToExitPressedOnce = false;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_home);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(tabPosition);
        tabPosition= 0;

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FragmentDrawer drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
         drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.root_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }




    private void init(){
        try{
            purchaseService = new PurchaseServiceImpl(this);
            tabPosition = getIntent().getIntExtra("tabPosition",0);
           /* Account account = co.in.mobilepay.sync.MobilePaySyncAdapter.getSyncAccount(this);
            ContentResolver.setIsSyncable(account,getString(R.string.auth_type),1);
            ContentResolver.setSyncAutomatically(account, getString(R.string.auth_type), true);
            ContentResolver.addPeriodicSync(account, getString(R.string.auth_type), Bundle.EMPTY, 60);*/
            /*cardService = new CardServiceImpl(this);*/
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new PurchaseListFragment(), "Shopping Bag");
        adapter.addFragment(new OrderStatusListFragment(), "Order status");
        adapter.addFragment(new PurHistoryListFragment(), "History");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void purchaseListOnClick(int purchaseId,int fragmentOptions) {
// TODO Need to call PurchaseDetails Activity
        Intent intent = new Intent(this, PurchaseDetailsActivity.class);
        intent.putExtra("purchaseId",purchaseId);
        intent.putExtra("fragmentOptions",fragmentOptions);
        startActivity(intent);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                int size = getSupportFragmentManager().getBackStackEntryCount();
                if(size > 0){
                    getSupportFragmentManager().popBackStack();
                }
                break;
        }
        return true;
    }


    public PurchaseService getPurchaseService() {
        return purchaseService;
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
    private void displayView(int position) {
        switch (position) {
            case 0: //Account
                Intent intent = new Intent(this, NaviDrawerActivity.class);
                intent.putExtra("options",1);
                startActivity(intent);
                //finish();
                break;
            case 1: //QR Code
                intent = new Intent(this, NaviDrawerActivity.class);
                intent.putExtra("options",2);
                startActivity(intent);
                break;
            case 2: //Support
                intent = new Intent(this, NaviDrawerActivity.class);
                intent.putExtra("options",3);
                startActivity(intent);
                break;
            case 3: //Terms of service
                intent = new Intent(this, NaviDrawerActivity.class);
                intent.putExtra("options",4);
                startActivity(intent);
                break;
            case 4: //About
                intent = new Intent(this, NaviDrawerActivity.class);
                intent.putExtra("options",5);
                startActivity(intent);
                break;

            case 5: //Rate the App
                intent = new Intent(this, NaviDrawerActivity.class);
                intent.putExtra("options",6);
                startActivity(intent);
                break;
            default:
                break;
        }

        /*if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }*/
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_PHONE_STATE},
                REQUEST_CODE_ASK_PERMISSIONS);
    }


    public void makeCall(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)) {
                showMessageOKCancel(getString(R.string.call_permission),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermission();
                            }
                        });
                return;
            }

            requestPermission();
            return;
        }else{
            makeCall();
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), okListener)
                .setNegativeButton(getString(R.string.cancel), null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    makeCall();
                } else {
                    // Permission Denied
                    Toast.makeText(this, getString(R.string.call_permission_denied), Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        MobilePayAnalytics.getInstance().trackScreenView("Home Screen");
        super.onResume();
    }

    private void makeCall(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + mobileNumber));
        try {
            startActivity(callIntent);  // TODO -- Need to handle request
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Thanks to StackOver flow
     * http://stackoverflow.com/questions/8430805/clicking-the-back-button-twice-to-exit-an-activity
     */
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            ActivityUtil.IS_LOGIN = false;
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.exit_double_click), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
}

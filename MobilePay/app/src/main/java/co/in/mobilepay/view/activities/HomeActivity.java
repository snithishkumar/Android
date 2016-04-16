package co.in.mobilepay.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.R;
import co.in.mobilepay.service.CardService;
import co.in.mobilepay.service.PurchaseService;
import co.in.mobilepay.service.impl.CardServiceImpl;
import co.in.mobilepay.service.impl.PurchaseServiceImpl;
import co.in.mobilepay.view.adapters.PurchaseListAdapter;
import co.in.mobilepay.view.fragments.FragmentDrawer;
import co.in.mobilepay.view.fragments.OrderStatusListFragment;
import co.in.mobilepay.view.fragments.PurHistoryListFragment;
import co.in.mobilepay.view.fragments.ProductsDetailsFragment;
import co.in.mobilepay.view.fragments.PurchaseListFragment;

public class HomeActivity extends AppCompatActivity implements PurchaseListAdapter.PurchaseListClickListeners,FragmentDrawer.FragmentDrawerListener{
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;

    private PurchaseService purchaseService;
    private CardService cardService;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
         drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.root_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
    }

    private void init(){
        try{
            purchaseService = new PurchaseServiceImpl(this);
            cardService = new CardServiceImpl(this);
            /*Account account = co.in.mobilepay.sync.MobilePaySyncAdapter.getSyncAccount(this);
            ContentResolver.setIsSyncable(account,getString(R.string.auth_type),1);
            ContentResolver.setSyncAutomatically(account, getString(R.string.auth_type), true);
            ContentResolver.addPeriodicSync(account, getString(R.string.auth_type), Bundle.EMPTY, 60);
            cardService = new CardServiceImpl(this);*/
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new PurchaseListFragment(), "Purchase");
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
    public void purchaseListOnClick(int purchaseId) {
// TODO Need to call PurchaseDetails Activity
        Intent intent = new Intent(this, PurchaseDetailsActivity.class);
        intent.putExtra("purchaseId",purchaseId);
        startActivity(intent);
    }
    public void showProductListFragment(){
        ProductsDetailsFragment productsDetailsFragment = new ProductsDetailsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_layout, productsDetailsFragment)
                .addToBackStack(null)
                .commit();
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

    public CardService getCardService() {
        return cardService;
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                Intent intent = new Intent(this, NaviDrawerActivity.class);
                intent.putExtra("options",1);
                startActivity(intent);
                finish();
                break;
            case 1:
                /*fragment = new FriendsFragment();
                title = getString(R.string.title_friends);*/
                break;
            case 2:
                /*fragment = new MessagesFragment();
                title = getString(R.string.title_messages);*/
                break;
            case 3:
                intent = new Intent(this, NaviDrawerActivity.class);
                intent.putExtra("options",3);
                startActivity(intent);
                finish();
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
}

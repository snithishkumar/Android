package co.in.mobilepay.view.activities;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import co.in.mobilepay.view.fragments.NewCardFragment;
import co.in.mobilepay.view.fragments.SaveCardsFragment;
import co.in.mobilepay.view.fragments.ProductsDetailsFragment;
import co.in.mobilepay.view.fragments.PurchaseListFragment;

public class HomeActivity extends AppCompatActivity implements PurchaseListAdapter.PurchaseListClickListeners{
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;

    private PurchaseService purchaseService;
    private CardService cardService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void init(){
        try{
            purchaseService = new PurchaseServiceImpl(this);

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

        adapter.addFragment(new PurchaseListFragment(), "Home");
        adapter.addFragment(new PurchaseListFragment(), "History");
        adapter.addFragment(new SaveCardsFragment(), "Payment");
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
        finish();
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
    public void showNewCardFragment(View view){
        Intent intent = new Intent(this, NewSaveCardActivity.class);
        startActivity(intent);
        finish();
    }

    public PurchaseService getPurchaseService() {
        return purchaseService;
    }

    public CardService getCardService() {
        return cardService;
    }
}

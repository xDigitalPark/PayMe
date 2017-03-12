package apps.digitakpark.payme;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import apps.digitakpark.payme.balance.listeners.OnTabSelectedListener;
import apps.digitakpark.payme.balance.ui.BalanceFragment;
import apps.digitakpark.payme.create.ui.CreateDebtActivity;
import apps.digitakpark.payme.list.tocharge.ToChargeFragment;
import apps.digitakpark.payme.preferences.PreferencesActivity;
import payme.pe.apps.digitakpark.payme.R;
import apps.digitakpark.payme.list.topay.ToPayFragment;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private boolean mine = false;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mine = tab.getPosition() == 1;
                if (tab.getPosition() == 2) {
                    hideFloatingButton(true);
                    Object instance = mSectionsPagerAdapter.instantiateItem(mViewPager, tab.getPosition());
                    if (instance != null) {
                        OnTabSelectedListener fragment = (OnTabSelectedListener) instance;
                        if (fragment != null)
                            fragment.onResumeFragment();
                    }
                } else {
                    if (tab.getPosition() == 1) {
                        OnTabSelectedListener fragment = (OnTabSelectedListener) mSectionsPagerAdapter.instantiateItem(mViewPager, tab.getPosition());
                        if (fragment != null)
                            fragment.onResumeFragment();
                    }
                    hideFloatingButton(false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateDebtActivity.class);
                intent.putExtra("debt_mine", mine);
                startActivity(intent);
            }
        });
    }

    private void hideFloatingButton(boolean hide) {
        fab.setVisibility(hide?View.GONE:View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        } else
        if(id == R.id.action_about_app) {
            Snackbar.make(mViewPager, "Coded by Erikson Murrugarra. DigitalPark, PE", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else if (id == R.id.action_settings) {
            navigateToSettingsActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void navigateToSettingsActivity() {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ToChargeFragment();
                case 1:
                    return new ToPayFragment();
                case 2:
                    return new BalanceFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.activity_main_tabs_medeben);
                case 1:
                    return getString(R.string.activity_main_tabs_lesdebo);
                case 2:
                    return getString(R.string.activity_main_tabs_cuenta);
            }
            return null;
        }
    }
}

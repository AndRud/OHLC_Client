package com.andrutyk.ohlc_client;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andrutyk.ohlc_client.main_fragment.MainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private final static String FRAGMENT_TAG = "main_fragment";

    private Fragment fragmentMain;

    String[] dataSets;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.left_drawer)
    ListView drawerList;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        addFragment();
        initDrawer();
    }

    private void addFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentMain = getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (fragmentMain == null) {
            fragmentMain = new MainFragment();
            fragmentTransaction.add(R.id.fragmentContent, fragmentMain, FRAGMENT_TAG);
        } else {
            fragmentTransaction.replace(R.id.fragmentContent, fragmentMain);
        }
        fragmentTransaction.commit();

    }

    private void initDrawer() {
        dataSets = getResources().getStringArray(R.array.data_provider_array);
        drawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, dataSets));
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, null,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }
        };
        drawerList.setOnItemClickListener(this);
        drawerLayout.addDrawerListener(drawerToggle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        selectDrawerItem(0);
    }

    private void selectDrawerItem(int position) {
        drawerList.setItemChecked(position, false);
        drawerLayout.closeDrawer(drawerList);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(dataSets[position]);
        }
        if (fragmentMain != null) {
            ((MainFragment) fragmentMain).setDataSet(dataSets[position]);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        selectDrawerItem(position);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        ((MainFragment)fragmentMain).setData(((MainFragment)fragmentMain).getData());
        super.onDestroy();
    }
}

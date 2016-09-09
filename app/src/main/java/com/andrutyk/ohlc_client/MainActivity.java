package com.andrutyk.ohlc_client;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andrutyk.ohlc_client.main_fragment.MainFragment;
import com.andrutyk.ohlc_client.utils.ConnectionDetector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    private final static String FRAGMENT_TAG = "main_fragment";

    private Fragment fragmentMain;

    String[] providers;

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

        initDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternet();
    }

    private void checkInternet() {
        if (isConnected()){
            showSettingDialog();
        } else {
            addFragment();
        }
    }

    private boolean isConnected() {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        return !connectionDetector.isConnectingToInternet();
    }

    private void showSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.question_internet_conn);
        builder.setPositiveButton(R.string.settings, (dialog, which) ->
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0));
        builder.setNegativeButton(R.string.dismiss, (dialog, which) -> dialog.cancel());
        builder.create().show();
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
        providers = getResources().getStringArray(R.array.data_provider_array);
        drawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, providers));
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
            getSupportActionBar().setTitle(providers[position]);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (fragmentMain != null) {
            ((MainFragment) fragmentMain).setProvider(position);
        }
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
}

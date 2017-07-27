package com.fantasygame.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.data.model.NavDrawerItem;
import com.fantasygame.data.model.response.LogoutResponse;
import com.fantasygame.define.Navigator;
import com.fantasygame.service.ConnectionService;
import com.fantasygame.ui.matches.MatchesFragment;
import com.fantasygame.ui.setting.SettingFragment;
import com.fantasygame.ui.sport.SportFragment;
import com.fantasygame.ui.teams.TeamsFragment;
import com.fantasygame.utils.PreferenceUtils;
import com.fantasygame.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity implements MainView, ConnectionService.postResultConnection {

    final int[] Menu_Icons = {R.drawable.info, R.drawable.setting, R.drawable.ic_matches, R.drawable.ic_teams, R.drawable.logout};

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.list_slidermenu)
    ListView mDrawerList;
    @Bind(R.id.txt_drawyer_button)
    TextView txt_drawyer_button;
    @Bind(R.id.txt_mainbackbutton)
    TextView txt_mainbackbutton;
    @Bind(R.id.txt_mainsettext)
    TextView txt_mainsettext;
    @Bind(R.id.tvConnection)
    TextView tvConnection;
    @Bind(R.id.frame_container)
    FrameLayout frame_container;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    ActionBarDrawerToggle mDrawerToggle;
    CharSequence mDrawerTitle;
    String[] navMenuTitles;
    CharSequence mTitle;
    ArrayList<NavDrawerItem> navDrawerItems;
    NavDrawerListAdapter adapter;
    boolean doubleBackToExitPressedOnce;
    static MainActivity self;
    ConnectionService connectionService;
    MainPresenter presenter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initComponent(savedInstanceState);
      //  initConnectionService();
    }

    private void initComponent(Bundle savedInstanceState) {
        self = this;
        progressDialog = new ProgressDialog(this);
        presenter = new MainPresenter();
        presenter.bindView(this);

        mTitle = mDrawerTitle = getTitle();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navDrawerItems = new ArrayList<NavDrawerItem>();
        for (int i = 0; i < Menu_Icons.length; i++) {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], Menu_Icons[i]));
        }
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, // nav
                R.string.app_name, // nav drawer open - description for
                R.string.app_name // nav drawer close - description for
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (savedInstanceState == null) {
            displayView(0);
        }
    }

    @Override
    public void hideLoadingUI() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingUI() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorLoadingUI(@NonNull Throwable throwable) {
        hideLoadingUI();
        Utils.showToast(throwable.getMessage());
    }

    @Override
    public void showResultLogout(LogoutResponse response) {
        if (!response.result) return;
        Utils.showToast(getString(R.string.logout_successful));
        PreferenceUtils.saveToPrefs(getApplicationContext(), PreferenceUtils.PREFS_LogInLogOutCheck, "logout");
        Navigator.openLoginActivity(MainActivity.this);
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // display view for selected nav drawer item
            displayView(position);
        }

    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new SportFragment();
                break;
            case 1:
                fragment = new SettingFragment();
                break;
            case 2:
                fragment = new MatchesFragment();
                break;
            case 3:
                fragment = new TeamsFragment();
                break;
            case 4:
                String api_token = PreferenceUtils.getFromPrefs(this, PreferenceUtils.PREFS_ApiToken, "");
                if (api_token != null && !api_token.isEmpty())
                    presenter.logout(api_token);
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null)
                    .commit();
            mDrawerLayout.closeDrawer(mDrawerList);
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
        }
    }

    public void onClickBack() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        this.doubleBackToExitPressedOnce = true;
        Utils.showToast("Please click BACK again to exit");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void initConnectionService() {
        connectionService = new ConnectionService(this, tvConnection, frame_container, this);
        connectionService.registerBroadcastConnection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (connectionService == null) return;
//        connectionService.unregisterBroadcastConnection(this);
    }

    @Override
    public void postResultConnection(boolean isConnection) {

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        }

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (currentFragment instanceof SportFragment) {
            onClickBack();
        } else {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack();
        }
    }

    public static void settestMain(Context context, String str) {
        MainActivity.self.txt_mainsettext.setText(str);
    }

    public static void hideBack(Context context) {
        MainActivity.self.txt_mainbackbutton.setVisibility(View.GONE);
    }

    public static void showBack(Context context) {
        MainActivity.self.txt_mainbackbutton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.txt_drawyer_button)
    public void onClickDrawyer() {
        if (Utils.isCheckShowSoftKeyboard(MainActivity.self))
            Utils.hideSoftKeyboard(MainActivity.self);
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }

    @OnClick(R.id.txt_mainbackbutton)
    public void onClickMainBack() {
        if (Utils.isCheckShowSoftKeyboard(MainActivity.self))
            Utils.hideSoftKeyboard(MainActivity.self);
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
    }
}

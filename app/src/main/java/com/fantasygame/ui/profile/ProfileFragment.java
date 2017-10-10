package com.fantasygame.ui.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fantasygame.R;
import com.fantasygame.base.BaseFragment;
import com.fantasygame.data.model.response.ProfileGameResponse;
import com.fantasygame.data.model.response.ProfileGameResponse.Datum;
import com.fantasygame.data.model.response.ProfileSportResponse;
import com.fantasygame.define.Constant;
import com.fantasygame.listener.SimpleTabSelectedListener;
import com.fantasygame.utils.PreferenceUtils;
import com.fantasygame.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HP on 25/09/2017.
 */

public class ProfileFragment extends BaseFragment implements ProfileView {


    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.vpGame)
    ViewPager vpGame;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.iv_avatar)
    CircleImageView iv_avatar;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.tv_address)
    TextView tv_address;
    @Bind(R.id.tv_phone)
    TextView tv_phone;
    @Bind(R.id.tv_email)
    TextView tv_email;

    ProfilePresenter presenter;
    ProfilePagerAdapter adapter;
    List<String> SportTabs;
    List<String> SportCodeTabs;
    Map<Integer, List<Datum>> mapGames;
    String phone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        self.settestMain(self, "Profile");
        self.showBack(self);
        showInfoUser();
    }

    private void showInfoUser() {
        String avatar = PreferenceUtils.getFromPrefs(self, PreferenceUtils.PREFS_AVATAR, "");
        String name = PreferenceUtils.getFromPrefs(self, PreferenceUtils.PREFS_FULLNAME, "");
        String address = PreferenceUtils.getFromPrefs(self, PreferenceUtils.PREFS_ADDRESS, "");
        phone = PreferenceUtils.getFromPrefs(self, PreferenceUtils.PREFS_PHONE, "");
        String email = PreferenceUtils.getFromPrefs(self, PreferenceUtils.PREFS_EMAIL, "");

        Utils.loadAvatarFromURL(self, Constant.URL_ADDRESS_SERVER + avatar, iv_avatar);
        tv_name.setText(name);
        tv_address.setText(address);
        tv_email.setText(email);
        tv_phone.setText(phone);
    }

    private void setupPresenter() {
        presenter = new ProfilePresenter();
        presenter.bindView(this);
        presenter.getListSport(phone);
    }

    public void setupUI() {
        vpGame.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        adapter = new ProfilePagerAdapter(self.getSupportFragmentManager());
        vpGame.setAdapter(adapter);
        tabLayout.setupWithViewPager(vpGame);
        vpGame.setCurrentItem(0);
        tabLayout.setOnTabSelectedListener(new SimpleTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpGame.setCurrentItem(tab.getPosition());
            }
        });
        createContent();
    }

    private void createContent() {
        adapter.setTabMenu(SportTabs, mapGames);
        for (int i = 0; i < SportTabs.size(); i++) {
            TextView textView = (TextView) self.getLayoutInflater().inflate(R.layout.tab_text, null);
            textView.setText(SportTabs.get(i));
            textView.setSelected(i == vpGame.getCurrentItem());
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
        progressBar.setVisibility(View.GONE);
        Utils.showToast(throwable.getMessage());
    }

    @Override
    public void showResultSports(ProfileSportResponse response) {
        if (!response.result) return;
        for (ProfileSportResponse.Datum datum : response.data) {
            SportTabs.add(datum.name);
            SportCodeTabs.add(datum.id);
        }
        if (SportTabs.size() > 0) {
            presenter.getListGame(phone);
        }
    }

    @Override
    public void showResultGames(ProfileGameResponse response) {
        if (!response.result) return;
        int index_tab = 0;
        List<Datum> filterSport;
        for (String code : SportCodeTabs) {
            filterSport = new ArrayList<>();
            for (Datum datum : response.data) {
                if (datum.sport.equals(code)) {
                    filterSport.add(datum);
                }
            }
            if (filterSport.size() > 0) mapGames.put(index_tab++, filterSport);
        }
        setupUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        SportTabs = new ArrayList<>();
        SportCodeTabs = new ArrayList<>();
        mapGames = new HashMap<>();
        setupPresenter();
    }
}

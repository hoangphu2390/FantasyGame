package com.fantasygame.ui.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.fantasygame.data.model.response.ProfileGameResponse.Datum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 14/02/2017.
 */

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    protected List<String> listTabMenu;
    protected List<String> sport_code;
    protected List<Fragment> fragmentList;
    protected Map<Integer, List<Datum>> mapGames;


    public ProfilePagerAdapter(FragmentManager fm) {
        super(fm);
        listTabMenu = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    public void setTabMenu(List<String> listTabMenu, Map<Integer, List<Datum>> mapGames) {
        this.listTabMenu = listTabMenu;
        this.mapGames = mapGames;
        for (int i = 0; i < listTabMenu.size(); i++)
            fragmentList.add(TabGameFragment.newInstance(i, mapGames.get(Integer.valueOf(i))));
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return listTabMenu.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTabMenu.get(position);
    }
}

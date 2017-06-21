package com.fantasygame.ui.pick;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 14/02/2017.
 */

public class PickPagerAdapter extends FragmentStatePagerAdapter {

    protected List<String> listTabMenu;
    protected List<Fragment> fragmentList;


    public PickPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
        listTabMenu = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    public void setTabMenu(List<String> listTabMenu) {
        this.listTabMenu = listTabMenu;
        for (int i = 0; i < listTabMenu.size(); i++)
            fragmentList.add(TabPickFragment.newInstance(i));
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
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

package com.fantasygame.ui.matches;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 13/07/2017.
 */

public class MatchesPagerAdapter extends FragmentStatePagerAdapter {

    protected List<String> CodeSports, NameSports;
    protected List<Fragment> fragmentList;

    public MatchesPagerAdapter(FragmentManager fm) {
        super(fm);
        CodeSports = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    public void setTabMenu(List<String> CodeSports, List<String> NameSports) {
        this.CodeSports = CodeSports;
        this.NameSports = NameSports;
        for (int i = 0; i < CodeSports.size(); i++)
            fragmentList.add(TabMatchesFragment.newInstance(CodeSports.get(i)));
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
        return CodeSports.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NameSports.get(position);
    }
}

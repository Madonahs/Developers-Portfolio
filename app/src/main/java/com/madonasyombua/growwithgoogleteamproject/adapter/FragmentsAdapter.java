package com.madonasyombua.growwithgoogleteamproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayo on 2/9/2018.
 */

public class FragmentsAdapter extends FragmentStatePagerAdapter {

    //Contains the two fragments
    private final List<Fragment> fragmentList = new ArrayList<>();


    public FragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    //Gets the position of the fragments
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    //Adds Fragments to the list. In out case, there is only two. Register and Login
    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
    }
}

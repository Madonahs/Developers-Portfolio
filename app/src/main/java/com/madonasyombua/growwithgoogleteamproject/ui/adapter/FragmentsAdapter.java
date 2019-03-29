/*Copyright (c) 2018 Madona Syombua

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package com.madonasyombua.growwithgoogleteamproject.ui.adapter;

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

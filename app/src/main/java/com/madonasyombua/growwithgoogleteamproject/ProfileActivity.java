package com.madonasyombua.growwithgoogleteamproject;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.madonasyombua.growwithgoogleteamproject.dialogs.ProfileFragmentDialog;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProfileFragment;

public class ProfileActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener {
    private static String TAG = "profile-fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //TODO: Need to handle screen orientation changes
        FragmentManager fm = getSupportFragmentManager();
        ProfileFragment profileFragment = (ProfileFragment) fm.findFragmentByTag(TAG);

        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
            fm.beginTransaction().add(R.id.container, profileFragment, TAG).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

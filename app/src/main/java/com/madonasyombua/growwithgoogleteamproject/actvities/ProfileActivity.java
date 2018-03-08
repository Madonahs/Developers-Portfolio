package com.madonasyombua.growwithgoogleteamproject.actvities;

/**
 * Created by madona on 2/15/18.
 */
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProfileFragment;

public class ProfileActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private static final String TAG = "profile-fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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

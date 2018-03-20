package com.madonasyombua.growwithgoogleteamproject.actvities;

/**
 * Created by madona on 2/15/18.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.madonasyombua.growwithgoogleteamproject.R;


public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "profile-fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        /*FragmentManager fm = getSupportFragmentManager();
        ProfileFragment profileFragment = (ProfileFragment) fm.findFragmentByTag(TAG);

        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
            fm.beginTransaction().add(R.id.container, profileFragment, TAG).commit();
        }

         @Override
    public void onFragmentInteraction(Uri uri) {

    }
    }*/

    }
}

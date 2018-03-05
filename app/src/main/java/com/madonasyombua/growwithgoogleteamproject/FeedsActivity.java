package com.madonasyombua.growwithgoogleteamproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.madonasyombua.growwithgoogleteamproject.ui.fragment.AddFeedsFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.FeedsFragment;

public class FeedsActivity extends AppCompatActivity implements AddFeedsFragment.onFragmentInteraction{
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);
    }

    @Override
    public void receivedStringandImage(String title) {
        FeedsFragment fm = new FeedsFragment();
        fm.receivedStringandImage(title);
    }
}

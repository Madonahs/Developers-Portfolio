package com.madonasyombua.growwithgoogleteamproject;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.madonasyombua.growwithgoogleteamproject.Adapter.FragmentsAdapter;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentsAdapter fragmentsAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.container);
        setViewPager(viewPager);
    }

    private void setViewPager(ViewPager viewPager){
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());
        viewPager.setAdapter(adapter);
    }
}

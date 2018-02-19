package com.madonasyombua.growwithgoogleteamproject;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.madonasyombua.growwithgoogleteamproject.Adapter.FragmentsAdapter;
import com.madonasyombua.growwithgoogleteamproject.databinding.ActivityMainBinding;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FragmentsAdapter fragmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setViewPager(binding.container);
    }

    private void setViewPager(ViewPager viewPager){
        fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager());
        fragmentsAdapter.addFragment(new LoginFragment());
        viewPager.setAdapter(fragmentsAdapter);


    }
}
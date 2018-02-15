package com.madonasyombua.growwithgoogleteamproject.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.madonasyombua.growwithgoogleteamproject.Adapter.FragmentsAdapter;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.databinding.ActivityLoginBinding;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.LoginFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.RegisterFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.intro.OnBoardingActivity;

import jonathanfinerty.once.Once;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private static final String TAG = "LoginActivity";
    static final int SHOW_INTRO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        /***
         * NOTE: Removed the welcome text since we already got a pretty cool logo
         */


        // Buttons initial state

        binding.btnLogin.setBackgroundResource(R.drawable.button_rounded_focused);
        binding.btnRegister.setBackgroundResource(R.drawable.button_rounded_normal);

        //this will set login fragment as the first thing you see
        setViewPager(binding.container);

        //Switch between login fragment and register fragment
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle buttons
                binding.btnLogin.setBackgroundResource(R.drawable.button_rounded_normal);
                binding.btnRegister.setBackgroundResource(R.drawable.button_rounded_focused);
                Toast.makeText(LoginActivity.this, "Going to register fragment", Toast.LENGTH_SHORT).show();
                registerFragment();
            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle buttons
                binding.btnLogin.setBackgroundResource(R.drawable.button_rounded_focused);
                binding.btnRegister.setBackgroundResource(R.drawable.button_rounded_normal);


                Toast.makeText(LoginActivity.this, "Going to login fragment", Toast.LENGTH_SHORT).show();
                setViewPager(binding.container);
            }
        });

        //setup the onboarding activity
        Once.initialise(this);
        if (!Once.beenDone(Once.THIS_APP_INSTALL, "showTutorial")) {
            Log.i(TAG, "onCreate: First time");
            startActivityForResult(new Intent(this, OnBoardingActivity.class), SHOW_INTRO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SHOW_INTRO) {
            if (resultCode == RESULT_OK) {
                Once.markDone("showTutorial");
            }
        }
    }


    private void setViewPager(ViewPager viewPager) {
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());
        viewPager.setAdapter(adapter);
    }

    private void registerFragment() {
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegisterFragment());
        binding.container.setAdapter(adapter);
    }

    // TODO: 2/9/2018 Add the social network button
}

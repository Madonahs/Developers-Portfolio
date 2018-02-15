package com.madonasyombua.growwithgoogleteamproject.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.madonasyombua.growwithgoogleteamproject.Adapter.FragmentsAdapter;
import com.madonasyombua.growwithgoogleteamproject.ProfileActivity;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.databinding.ActivityLoginBinding;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.LoginFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.RegisterFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.intro.OnBoardingActivity;

import java.util.Arrays;

import jonathanfinerty.once.Once;

public class LoginActivity extends AppCompatActivity {

    Button mFacebookLoginButton, mGoogleLoginButton;
    CallbackManager mCallbackManager;

    // Facebook permission
    private static final String PUBLIC_PROFILE_PERMISSION = "public_profile";
    private static final String EMAIL_PERMISSION = "email";

    private ActivityLoginBinding binding;
    private static final String TAG = "LoginActivity";
    static final int SHOW_INTRO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mCallbackManager = CallbackManager.Factory.create();

        mFacebookLoginButton = (Button) findViewById(R.id.btn_facebook_login);
        mGoogleLoginButton = (Button) findViewById(R.id.btn_google_login);

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

        // Custom facebook login button
        mFacebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList(PUBLIC_PROFILE_PERMISSION, EMAIL_PERMISSION));
            }
        });


        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getBaseContext(), R.string.facebook_login_success, Toast.LENGTH_LONG).show();

                // TODO: Use the returned token from loginResult to make a graph API request for user info (name, email, ....)
                // For now, redirect user to the profile activity
                Intent profileIntent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(profileIntent);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getBaseContext(), R.string.facebook_login_cancel, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getBaseContext(), R.string.facebook_login_error, Toast.LENGTH_LONG).show();
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
        } else if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()){
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
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

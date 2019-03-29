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
package com.madonasyombua.growwithgoogleteamproject.ui.activities;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.ui.adapter.FragmentsAdapter;
import com.madonasyombua.growwithgoogleteamproject.databinding.ActivityLoginBinding;
import com.madonasyombua.growwithgoogleteamproject.ui.login.AppLoginManager;
import com.madonasyombua.growwithgoogleteamproject.ui.login.LoginStatusManager;
import com.madonasyombua.growwithgoogleteamproject.data.models.User;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.LoginFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.RegisterFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.intro.OnBoardingActivity;
import com.madonasyombua.growwithgoogleteamproject.util.Constant;

import java.util.Arrays;

import jonathanfinerty.once.Once;



public class LoginActivity extends AppCompatActivity implements AppLoginManager.LoginInterface, View.OnClickListener {

    CallbackManager mCallbackManager;

    // Facebook permissions - public profile, email
    private static final String PUBLIC_PROFILE_PERMISSION = "public_profile";
    private static final String EMAIL_PERMISSION = "email";

    private ActivityLoginBinding binding;
    private static final String TAG = LoginActivity.class.getName();
    private static final int SHOW_INTRO = 1;

    GoogleSignInClient mGoogleSignInClient;
    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Send user to Main activity if they're already signed in*/
        if(LoginStatusManager.getLoginStatus(this) &&
                FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mCallbackManager = CallbackManager.Factory.create();

        // Binding and listening to all the buttons
        binding.btnLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
        binding.btnFacebookLogin.setOnClickListener(this);
        binding.btnGoogleLogin.setOnClickListener(this);

        // Buttons initial state
        binding.btnLogin.setBackgroundResource(R.drawable.button_rounded_focused);
        binding.btnRegister.setBackgroundResource(R.drawable.button_rounded_normal);

        //this will set login fragment as the first thing you see
        setViewPager(binding.container);

        // Google login set up
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestEmail()
                                        .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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

        } else if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {

            Task<GoogleSignInAccount> gTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(gTask);
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
    /*private void loginFragment(){
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());
        binding.container.setAdapter(adapter);
    }*/

    public void showHideProgressBar(boolean show){
        if(show){
            binding.loginLoader.setVisibility(View.VISIBLE);
        } else {
            binding.loginLoader.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSigninSuccess(User user) {
        Intent intent = new Intent(this, MainActivity.class)
                .putExtra(Constant.USER,user.bundleUp());
        startActivity(intent);

        showHideProgressBar(false);

        LoginStatusManager.storeLoginStatus(this,true);

        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRegistrationSuccess() {
        showHideProgressBar(false);
        Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSigninFailed() {
        showHideProgressBar(false);
    }

    @Override
    public void onRegistrationFailed() {
        showHideProgressBar(false);
    }

    @Override
    public void onResetPasswordSuccess() {
        Toast.makeText(this, "onResetPasswordSuccess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResetPasswordFailed() {
        Toast.makeText(this, "onResetPasswordFailed", Toast.LENGTH_SHORT).show();
    }

    /***
     * Handle login process when the login button is pressed
     */
    private void handleLoginProcess() {
        // Toggle buttons
        binding.btnLogin.setBackgroundResource(R.drawable.button_rounded_focused);
        binding.btnRegister.setBackgroundResource(R.drawable.button_rounded_normal);
        setViewPager(binding.container);

    }

    /*
     * Handle registration process when the register button is clicked
     */
    private void handleRegistrationProcess() {
        // Toggle buttons
        binding.btnLogin.setBackgroundResource(R.drawable.button_rounded_normal);
        binding.btnRegister.setBackgroundResource(R.drawable.button_rounded_focused);
        registerFragment();

    }

    /*
     * Handle facebook log in
     */
    private void signInWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList(PUBLIC_PROFILE_PERMISSION, EMAIL_PERMISSION));
    }

    /*
     * Handle google sign in process
     */
    private void signInWithGoogle() {

        // Create a google sign in intent
        Intent googleSignInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(googleSignInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
    }

    /*
     * Handles google sign in result
     *
     * @param completedTask - completed GoogleSignInAccount task
     */
    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Successfully signed in
            if (account != null) {

                // TODO: All user's basic info such as name can now be obtained from the account object
                String personName = account.getDisplayName();
                String personEmail = account.getEmail();

                // For debugging purposes only
                Log.d(TAG, "Google info " + personName + " - " + personEmail);

                // Start MainActivity
                Intent mainActivityIntent = new Intent(getBaseContext(), MainActivity.class);

                // TODO: User info can be passed in to the MainActivity as EXTRA or stored locally in db or Firebase
                startActivity(mainActivityIntent);
            }

        } catch (ApiException e) {

            // Google sign in failed
            e.printStackTrace();
        }

    }

    /***
     * Detect click events for all the different views (buttons)
     *
     * @param v - view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_register:
                handleRegistrationProcess();
                break;
            case R.id.btn_login:
                handleLoginProcess();
                break;

            case R.id.btn_facebook_login:
                signInWithFacebook();
                break;

            case R.id.btn_google_login:
                signInWithGoogle();
                break;
        }
    }
}

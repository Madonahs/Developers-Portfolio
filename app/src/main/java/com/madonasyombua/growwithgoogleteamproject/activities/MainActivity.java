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
package com.madonasyombua.growwithgoogleteamproject.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.login.LoginStatusManager;
import com.madonasyombua.growwithgoogleteamproject.models.User;
import com.madonasyombua.growwithgoogleteamproject.ui.SharedPref;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.FeedsFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.InterestFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.PostFeedFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProfileFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProjectsFragment;
import com.madonasyombua.growwithgoogleteamproject.util.BottomNavigationViewHelper;
import com.madonasyombua.growwithgoogleteamproject.util.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity
        extends AppCompatActivity
        implements FeedsFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener,
        SharedPreferences.OnSharedPreferenceChangeListener,
        PostFeedFragment.OnFragmentInteractionListener {

    private Fragment fragment;
    private static final String TAG = MainActivity.class.getName();
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.drawer_container)DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)NavigationView navView;
    private CircleImageView profilePicView;
    private TextView userName;
    private TextView userProfession;
    SharedPref sharedPref;
    private boolean prev_State = false;
    private User user;
    private static int uid;


    /**
     * Theme can only be changed before setContentView is called.
     * Therefore, I am changing the theme on here.
     * store data
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        prev_State = sharedPref.loadNightModeState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //This method will save the theme and initiates the preferenceChange listener
        setCorrectTheme();

        // Get all user information views from the drawer header view
        View drawerHeaderView = navView.getHeaderView(0);
        profilePicView = drawerHeaderView.findViewById(R.id.drawer_header_user_image);
        userName = drawerHeaderView.findViewById(R.id.drawer_header_user_name);
        userProfession = drawerHeaderView.findViewById(R.id.drawer_header_user_profession);

        //Set action bar, navigation drawer, navigation drawer header
        setSupportActionBar(toolbar);
        setupNavDrawer();
        setDrawerHeader();
        fragment = getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new FeedsFragment();
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment, TAG);
        transaction.commit();

        /*TODO: Get user data from intent or load from DB if intent is null*/
        Bundle data = getIntent().getBundleExtra(Constant.USER);
        if (data != null)
            user = User.build(data);
        else
            //TODO: load from DB
            user = new User("this guy", "thisguy@devs.com", "000000");
    }

    /**
     * Set up the drawer
     */
    private void setupNavDrawer() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
            navView.setNavigationItemSelectedListener(this);
            navView.setItemIconTintList(null);
        }
    }

    /**
     * Set user information - profile pic, name, profession
     * Logo image, sample username, sample user profession used here
     * Can be swapped with Picasso or Glide image loader
     * currently dummy data
     */
    private void setDrawerHeader() {

        profilePicView.setImageResource(R.drawable.madonah);
        userName.setText(getString(R.string.about_developer_name1));
        userProfession.setText(getString(R.string.dummy_position));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            boolean isOnStack = false;
            switch (item.getItemId()) {
                case R.id.action_feeds:
                    fragment = new FeedsFragment();
                    for(Fragment frag:fragments){
                        /* Check if fragment is on stack*/
                        if(frag instanceof FeedsFragment){
                            isOnStack = true;
                            break;
                        }
                    }
                    break;
                case R.id.action_interests:
                    fragment = new InterestFragment();
                    for(Fragment frag:fragments){
                        /* Check if fragment is on stack*/
                        if(frag instanceof InterestFragment){
                            isOnStack = true;
                            break;
                        }
                    }
                    break;
                case R.id.action_projects:
                    fragment = new ProjectsFragment();
                    for(Fragment frag:fragments){
                        /* Check if fragment is on stack*/
                        if(frag instanceof ProjectsFragment){
                            isOnStack = true;
                            break;
                        }
                    }
                    break;
                case R.id.action_profile:
                    fragment = ProfileFragment.newInstance(user);
                    for(Fragment frag:fragments){
                        /* Check if fragment is on stack*/
                        if(frag instanceof ProfileFragment){
                            isOnStack = true;
                            break;
                        }
                    }
                    break;
            }
            /*
                Prevent duplicate record on back stack and keep consistent back navigation
             */
            if (!isOnStack)
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, fragment, TAG)
                        .addToBackStack(null)
                        .commit();

            return true;
        }

    };


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(Constant.USER, user.bundleUp());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        user = User.build(savedInstanceState.getBundle(Constant.USER));
    }

    /**
     * When back button pressed hide navigation drawer if open else move task to back
     */

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            /* Manages back navigation. Better than quiting the activity */
            if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                getSupportFragmentManager().popBackStackImmediate();
            else
                moveTaskToBack(true);
        }
    }

    /**
     * @param item item
     * @return After implementation return true for the below cases
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.messages:
                intent = new Intent(this, MessageActivity.class);
                startActivity(intent);
                return true;
            case R.id.manage_profile:
                intent = new Intent(this, ManageProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.help:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                /* Sign the user out out the app */
                FirebaseAuth.getInstance().signOut();
                LoginStatusManager.storeLoginStatus(this, false);
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

        }
        return false;

    }

    /**
     * Implement Navigation Drawer list item click listener
     *
     * @param uri
     */

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDialogSubmit() {

    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //parent, view, position , id

    }

    @Override
    public void onDialogSubmit(PostFeedFragment dialog, String text, String fileName) {

    }

    /**
     * the sharedP is going to change
     *
     * @param sharedPreferences
     * @param key
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("enable_dark_mode")) {
            sharedPref.setNightModeState(sharedPreferences.getBoolean(key, false));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setCorrectTheme() {

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.setNightModeState(sharedPreference.getBoolean("enable_dark_mode", false));
        sharedPreference.registerOnSharedPreferenceChangeListener(this);

    }

    /**
     * Activities must be started again to show the theme change,
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (prev_State != sharedPref.loadNightModeState()) {
            startActivity(new Intent(this, this.getClass()));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * Converts density pixels to pixels.
     *
     * @param dp   Density pixels
     * @param view The view
     * @return Density pixels
     */
    public static float dpToPixels(int dp, View view) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, view.getResources().getDisplayMetrics());
    }

    /**
     * @return The uid of the signed in user.
     */
    public static int getUid() {
        return uid;
    }
}


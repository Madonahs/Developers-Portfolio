package com.madonasyombua.growwithgoogleteamproject.actvities;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.SearchViewTest;
import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener;
import com.madonasyombua.growwithgoogleteamproject.ui.SharedPref;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.FeedsFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.InterestFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProfileFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProjectsFragment;
import com.madonasyombua.growwithgoogleteamproject.util.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity
        extends AppCompatActivity
        implements OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener,  SharedPreferences.OnSharedPreferenceChangeListener {

    private Fragment fragment;
    private static final String TAG = "current-frag";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_container)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;

    private CircleImageView profilePicView;
    private TextView userName;
    private TextView userProfession;
    SharedPref sharedPref;
    private boolean prev_State = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme can only be changed before setContentView is called.
        //Therefore, I am changing the theme on here.
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

        /** Get all user information views from the drawer header view*/
        View drawerHeaderView = navView.getHeaderView(0);
        profilePicView = drawerHeaderView.findViewById(R.id.drawer_header_user_image);
        userName = drawerHeaderView.findViewById(R.id.drawer_header_user_name);
        userProfession = drawerHeaderView.findViewById(R.id.drawer_header_user_profession);

        /**Set action bar, navigation drawer, navigation drawer header*/
        setSupportActionBar(toolbar);
        setupNavDrawer();
        setDrawerHeader();
        fragment = getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new FeedsFragment();

        }


        //This is my bottom navigator for easy navigation couldn't draw this on my mockup
        // since it was difficult to squeeze everything.

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment, TAG);
        transaction.commit();
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
     */
    private void setDrawerHeader() {
        // Logo image, sample username, sample user profession used here
        // Can be swapped with Picasso or Glide image loader
        profilePicView.setImageResource(R.drawable.madonah);
        userName.setText(getString(R.string.about_developer_name1));
        userProfession.setText(getString(R.string.dummy_position));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // we will start the fragments once we have worked on them.
            switch (item.getItemId()) {
                case R.id.action_feeds:
                   fragment = new FeedsFragment();
                    break;
                case R.id.action_interests:
                    fragment = new InterestFragment();
                    break;
                case R.id.action_projects:
                    fragment = new ProjectsFragment();
                    break;
                case R.id.action_profile:
                    fragment = new ProfileFragment();
                    break;
            }

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, fragment, TAG);
            transaction.commit();

            return true;
        }

    };

    /**When back button pressed hide navigation drawer if open else move task to back*/
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            moveTaskToBack(true);
        }
    }

    /** Implement Navigation Drawer list item click listener*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // After implementation return true for the below cases
        switch (item.getItemId()) {
            case R.id.messages:
                // Take user to messages screen
                return false;
            case R.id.manage_profile:
                // Take user to edit profile screen
                return false;

            case R.id.settings:
                // Take user to setting screen
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return false;

            case R.id.about:
                // Take user to about screen
                Intent intent1 = new Intent(this, AboutActivity.class);
                startActivity(intent1);
                return false;
            case R.id.help:
                // Take user to help screen
                Intent intent2 = new Intent(this, HelpActivity.class);
                startActivity(intent2);
                return false;
            case R.id.logout:
                //FIXME: 3:17:2018 this part is for log out, i know you might be trying it out
                // FIXME: but how about including this in setting, checkout the dark them implementation and follow that Setting has a lot of space or inside Help class
                Intent helpTest = new Intent(this, SearchViewTest.class);
                startActivity(helpTest);
                // logout
                return false;

        }
        return false;

    }

    /**
     * Implement Navigation Drawer list item click listener
     */

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //they sharedP is going to change
        if (key.equals("enable_dark_mode")){
            sharedPref.setNightModeState(sharedPreferences.getBoolean(key,false));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);    }

    private void setCorrectTheme(){

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.setNightModeState(sharedPreference.getBoolean("enable_dark_mode", false));
        sharedPreference.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Activities must be started again to show the theme change,
        if(prev_State != sharedPref.loadNightModeState())
        {
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

}


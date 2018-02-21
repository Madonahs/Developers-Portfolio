package com.madonasyombua.growwithgoogleteamproject;

import android.net.Uri;
import android.os.Bundle;
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

import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.FeedsFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.InterestFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProfileFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProjectsFragment;
import com.madonasyombua.growwithgoogleteamproject.util.BottomNavigationViewHelper;
import com.madonasyombua.growwithgoogleteamproject.util.Session;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity
        extends AppCompatActivity
        implements OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    //private ActivityMainBinding binding;
    //private FragmentsAdapter fragmentsAdapter;

    //fragment to start when login and sign up is successful
    private Fragment fragment = new FeedsFragment();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_container)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;

    private CircleImageView profilePicView;
    private TextView userName;
    private TextView userProfession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Get all user information views from the drawer header view
        View drawerHeaderView = navView.getHeaderView(0);
        profilePicView = drawerHeaderView.findViewById(R.id.drawer_header_user_image);
        userName = drawerHeaderView.findViewById(R.id.drawer_header_user_name);
        userProfession = drawerHeaderView.findViewById(R.id.drawer_header_user_profession);

        // Set action bar, navigation drawer, navigation drawer header
        setSupportActionBar(toolbar);
        setupNavDrawer();
        setDrawerHeader();

        // binding  = DataBindingUtil.setContentView(this,R.layout.activity_main_content);
        //setViewPager(binding.content);


        Session session = new Session(getBaseContext());
        /*
        if (session.getuserId() == 0) {
            // start Login Activity
             startActivity(new Intent(this, LoginActivity.class));
        }
        */

        //This is my bottom navigator for easy navigation couldn't draw this on my mockup
        // since it was difficult to squeeze everything.
        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    /** Set up the drawer */
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

    /** Set user information - profile pic, name, profession */
    private void setDrawerHeader() {
        // Logo image, sample username, sample user profession used here
        // Can be swapped with Picasso or Glide image loader
        profilePicView.setImageResource(R.drawable.logo);
        userName.setText(getString(R.string.sample_user_name));
        userProfession.setText(getString(R.string.sample_user_profession));
    }

    // Here i am trying to switch activities on my bottom navigation
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
            transaction.replace(R.id.content, fragment);
            transaction.commit();

            return true;
        }

    };

    /** When back button pressed hide navigation drawer if open else move task to back  */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            moveTaskToBack(true);
        }
    }

    /** Implement Navigation Drawer list item click listener */
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
            case R.id.about:
                // Take user to about screen
                return false;
            case R.id.help:
                // Take user to help screen
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    // i am cautious in using binding since i don't know much about it. Would love to learn more.
   /* private void setViewPager(ViewPager viewPager){
        fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager());
        fragmentsAdapter.addFragment(new LoginFragment());
        viewPager.setAdapter(fragmentsAdapter);
    }*/
}


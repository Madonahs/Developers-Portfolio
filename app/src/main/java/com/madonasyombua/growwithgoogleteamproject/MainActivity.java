package com.madonasyombua.growwithgoogleteamproject;
/**
 * Created by madona on 2/8/18.
 */

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.madonasyombua.growwithgoogleteamproject.interfaces.OnFragmentInteractionListener;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.FeedsFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.InterestFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProfileFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.ProjectsFragment;
import com.madonasyombua.growwithgoogleteamproject.util.BottomNavigationViewHelper;
import com.madonasyombua.growwithgoogleteamproject.util.Session;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    //private ActivityMainBinding binding;
    //private FragmentsAdapter fragmentsAdapter;

    //fragment to start when login and sign up is successful
   // private Fragment fragment = new FeedsFragment();

    //for now i will start nothing
    private Fragment fragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // binding  = DataBindingUtil.setContentView(this,R.layout.activity_main);
        //setViewPager(binding.content);
        Session session = new Session(getBaseContext());
        if (session.getuserId() == 0) {
            //start Login Activity.
           // startActivity(new Intent(this, LoginActivity.class));
        }

      //This is my bottom navigator for easy navigation couldn't draw this on my mockup since it was difficult to squeeze everything.
        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content, new FeedsFragment());
        transaction.commit();


    }
//i am cautious in using binding since i don't know much about it. Would love to learn more.

   /* private void setViewPager(ViewPager viewPager){
        fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager());
        fragmentsAdapter.addFragment(new LoginFragment());
        viewPager.setAdapter(fragmentsAdapter);


    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

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

            transaction.replace(R.id.content, fragment);
            return true;
        }

    };

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}


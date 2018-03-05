package com.madonasyombua.growwithgoogleteamproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;

public class AddFeeds extends AppCompatActivity {


    @BindView(R.id.toolbar_add_feed)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeds);
        setSupportActionBar(toolbar);

        // TODO: 3/4/2018 Add a title and a back button
//        toolbar.setTitle("Add to your Feeds");
    }
}

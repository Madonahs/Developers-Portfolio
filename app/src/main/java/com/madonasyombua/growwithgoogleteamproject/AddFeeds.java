package com.madonasyombua.growwithgoogleteamproject;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.madonasyombua.growwithgoogleteamproject.ui.fragment.FeedsFragment;

public class AddFeeds extends AppCompatActivity {

    private static final String TAG = "AddFeeds";
    TextInputEditText edit_project_name, edit_project_description;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeds);
//        setSupportActionBar(toolbar);

        // TODO: 3/4/2018 Add a title and a back button
//        toolbar.setTitle("Add to your Feeds");


        edit_project_name = (TextInputEditText)findViewById(R.id.edit_project_name);
        edit_project_description = findViewById(R.id.edit_project_description);

        btn = findViewById(R.id.button_add_feed);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
                finish();
            }
        });
    }

    private void sendData() {
        Bundle bundle = new Bundle();
        bundle.putString("FEEDS_TITLE", edit_project_name.getText().toString().trim());
        bundle.putString("FEEDS_DESCRIPTION", edit_project_description.getText().toString().trim());

        FeedsFragment fm = new FeedsFragment();
        fm.setArguments(bundle);

        Log.i(TAG, "sendData: " + bundle);
    }

}

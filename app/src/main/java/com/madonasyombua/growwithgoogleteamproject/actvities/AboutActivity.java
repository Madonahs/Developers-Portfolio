package com.madonasyombua.growwithgoogleteamproject.actvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.madonasyombua.growwithgoogleteamproject.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class AboutActivity extends AppCompatActivity {


    @BindView(R.id.backToNavDrawer)
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}

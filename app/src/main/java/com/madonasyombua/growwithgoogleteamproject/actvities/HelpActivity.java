package com.madonasyombua.growwithgoogleteamproject.actvities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.ui.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @BindView(R.id.backToMain)
    ImageButton backToMain;
    @BindView(R.id.faq)
    TextView faq;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.terms)
    TextView terms;
    SharedPref sharedPref;
    private boolean prev_State = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Theme can only be changed before setContentView is called.
        //Therefore, I am changing the theme on here.
        sharedPref = new SharedPref(this);
        if ( sharedPref.loadNightModeState() ) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
        prev_State = sharedPref.loadNightModeState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);

        setCorrectTheme();

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.madonahsyombua.com/faqs"));
                startActivity(intent);
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.madonahsyombua.com/contact"));
                startActivity(intent);
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.madonahsyombua.com/privacy-policy"));
                startActivity(intent);
            }
        });
    }

    private void setCorrectTheme() {

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.setNightModeState(sharedPreference.getBoolean("enable_dark_mode", false));
        sharedPreference.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //they sharedP is going to change
        if ( key.equals("enable_dark_mode") ) {
            sharedPref.setNightModeState(sharedPreferences.getBoolean(key, false));
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        //Activities must be started again to show the theme change,
        if ( prev_State != sharedPref.loadNightModeState() ) {
            startActivity(new Intent(this, this.getClass()));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);    }

}
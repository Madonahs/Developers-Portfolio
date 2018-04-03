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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.ui.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    // SearchView
    private ImageButton backToMain;
    @BindView(R.id.faq)TextView faq;
    @BindView(R.id.contact)TextView contact;
    private TextView terms;
    SharedPref sharedPref;
    private boolean prev_State = false;

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
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        setCorrectTheme();
        backToMain = findViewById(R.id.backToMain);
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

        terms.findViewById(R.id.terms);
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("enable_dark_mode")){
            sharedPref.setNightModeState(sharedPreferences.getBoolean(key,false));
        }
    }
}

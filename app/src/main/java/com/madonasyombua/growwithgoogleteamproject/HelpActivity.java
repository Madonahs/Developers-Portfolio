package com.madonasyombua.growwithgoogleteamproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

private TextView mfaq,mContact,mTerms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mfaq = findViewById(R.id.faq);
        mContact =findViewById(R.id.contact);
        mTerms = findViewById(R.id.terms);
        //go to the faq website page
        mfaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.madonahsyombua.com"));
                startActivity(intent);

            }
        });
        //display contact us info
        mContact.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.addCategory(Intent.CATEGORY_BROWSABLE);
                intent1.setData(Uri.parse("http://www.madonahsyombua.com"));
                startActivity(intent1);
            }
        });
        //terms and service
        mTerms.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.addCategory(Intent.CATEGORY_BROWSABLE);
                intent1.setData(Uri.parse("http://www.madonahsyombua.com"));
                startActivity(intent1);
            }
        });
        //go back to main activity
        ImageButton imageButton = findViewById(R.id.backToMain);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });



    }
}

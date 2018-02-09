package com.madonasyombua.growwithgoogleteamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    EditText emailEnter;
    EditText passwordEnter;
    Button loginButton;
    TextView signupLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// getting the EditText and Button Id on the Layout I will comment them for now then we can connect with the ids
       /*
        emailEnter=findViewById(R.id...);
        passwordEnter=findViewById(R.id...);
        loginButton=findViewById(R.id...);
        signupLink=findViewById(R.id...);*/


       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //looking at creating a login method Login(); to help with validation
           }
       });

       signupLink.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               //start the sign up Activity.
           }
       });
    }


    public void Login(){
        //  tag
        //looking at On complete call either onLoginSuccess or onLoginFailed
    }
}

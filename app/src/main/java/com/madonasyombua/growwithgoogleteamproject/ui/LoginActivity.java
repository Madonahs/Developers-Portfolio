package com.madonasyombua.growwithgoogleteamproject.ui;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.madonasyombua.growwithgoogleteamproject.Adapter.FragmentsAdapter;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.LoginFragment;
import com.madonasyombua.growwithgoogleteamproject.ui.fragment.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    TextView mWelcome;
    FragmentsAdapter fragmentsAdapter;
    ViewPager viewPager;
    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Including color in the TextView.
        mWelcome = findViewById(R.id.welcome);
        int[] color = {Color.rgb( 0, 0, 205),Color.CYAN};
        float[] position = {0, 1};
        Shader.TileMode tile_mode = Shader.TileMode.MIRROR;
        LinearGradient lin_grad = new LinearGradient(0, 0, 0, 200,color,position, tile_mode);
        Shader shader_gradient = lin_grad;
        mWelcome.getPaint().setShader(shader_gradient);


        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);

        //I used viewpager because it helps with switching between fragments
        viewPager = findViewById(R.id.container);

        //this will set login fragment as the first thing you see
        setViewPager(viewPager);

        //Switch between login fragment and register fragment
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Going to register fragment", Toast.LENGTH_SHORT).show();
                registerFragment();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Going to login fragment", Toast.LENGTH_SHORT).show();
                setViewPager(viewPager);
            }
        });

    }

    private void setViewPager(ViewPager viewPager){
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());
        viewPager.setAdapter(adapter);
    }

    private void registerFragment(){
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        adapter.addFragment(new RegisterFragment());
        viewPager.setAdapter(adapter);
    }

    // TODO: 2/9/2018 Can someone add design to the button and backgroung?

    // TODO: 2/9/2018 Add the social network button
}

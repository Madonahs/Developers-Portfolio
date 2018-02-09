package com.madonasyombua.growwithgoogleteamproject;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView mWelcome;
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


    }
}

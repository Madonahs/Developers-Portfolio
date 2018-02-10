package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.madonasyombua.growwithgoogleteamproject.R;

/**
 * Created by Ayo on 2/9/2018.
 */

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private EditText username, password;
    private Button btn_login;

    /*
    * This class is used to get data from the fragment like the username and stuff.
    * It also binds the views
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container, false);
        username = (EditText)view.findViewById(R.id.edit_username);
        password = (EditText)view.findViewById(R.id.edit_password);
        btn_login = (Button)view.findViewById(R.id.login_fragment_button);
        
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Logging in", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

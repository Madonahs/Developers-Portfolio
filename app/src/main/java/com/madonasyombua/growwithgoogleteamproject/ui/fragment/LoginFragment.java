package com.madonasyombua.growwithgoogleteamproject.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.madonasyombua.growwithgoogleteamproject.MainActivity;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.database.AppLoginManager;
import com.madonasyombua.growwithgoogleteamproject.databinding.FragmentLoginBinding;
import com.madonasyombua.growwithgoogleteamproject.models.User;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Ayo on 2/9/2018.
 */

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private static final String TAG = "LoginFragment";
    /*
    * This class is used to get data from the fragment like the username and stuff.
    * It also binds the views
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login, container, false);

        binding.loginFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_text = extractText(binding.editEmail);
                String password_text = extractText(binding.editPassword);

                AppLoginManager.signinUser(getActivity(), new User(email_text, password_text));
            }
        });

        return binding.getRoot();
    }

    private String extractText(EditText text) {
        String newText = text.getText().toString().trim();
        return newText;
    }
}



